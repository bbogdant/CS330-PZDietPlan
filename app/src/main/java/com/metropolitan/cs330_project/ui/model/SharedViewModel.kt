package com.metropolitan.cs330_project.ui.model

import androidx.annotation.OpenForTesting
import androidx.lifecycle.*

import com.metropolitan.cs330_project.data.database.model.Product
import com.metropolitan.cs330_project.data.database.model.DietList
import com.metropolitan.cs330_project.data.repository.DietListRepository
import com.metropolitan.cs330_project.data.status.ResultStatus
import com.metropolitan.cs330_project.ui.model.mapper.asDomainModel
import com.metropolitan.cs330_project.ui.model.mapper.asUiModel
import com.metropolitan.cs330_project.ui.model.model.MutableScreenUi
import com.metropolitan.cs330_project.ui.model.model.ProductUi
import com.metropolitan.cs330_project.ui.model.model.ScreenUi
import com.metropolitan.cs330_project.ui.model.model.DietListUi
import com.metropolitan.cs330_project.ui.model.state.ScreenState
import com.metropolitan.cs330_project.ui.model.state.DietListState
import com.metropolitan.cs330_project.util.ext.addSourceInvoke
import com.metropolitan.cs330_project.util.ext.mapNonNull
import kotlinx.coroutines.launch

@OpenForTesting
class SharedViewModel constructor(
    private val backStack: ScreenBackStack,
    private val dietListRepository: DietListRepository
) : ViewModel(), ScreenBackStack by backStack {

    private val currentScreenState = backStack.getCurrentScreen()

    private val _updateDietListLoading = MutableLiveData<Boolean>()
    private val updateDietListLoading: LiveData<Boolean> = _updateDietListLoading

    private val _deleteProductLoading = MutableLiveData<Boolean>()
    private val deleteProductLoading: LiveData<Boolean> = _deleteProductLoading

    private val _createProductLoading = MutableLiveData<Boolean>()
    private val createProductLoading: LiveData<Boolean> = _createProductLoading

    private val _createDietListLoading = MutableLiveData<Boolean>()
    private val createDietListLoading: LiveData<Boolean> = _createDietListLoading

    private val dietListStatus = mapNonNull(currentScreenState) {
        when (it) {
            is ScreenState.CurrentDietList -> DietListState.CURRENT
            is ScreenState.ArchivedDietList -> DietListState.ARCHIVED
            else -> null
        }
    }

    private val dietLists = dietListStatus.switchMap {
        when (it) {
            DietListState.CURRENT -> dietListRepository.getCurrentShoppingList()
            DietListState.ARCHIVED -> dietListRepository.getArchivedShoppingList()
        }
    }

    private val dietListsUi = mapNonNull(dietLists) {
        when (it) {
            is ResultStatus.Loading -> ResultStatus.Loading
            is ResultStatus.Success -> ResultStatus.Success(it.data.asUiModel())
            is ResultStatus.Error -> it
        }
    }

    private val selectedDietList = mapNonNull(currentScreenState) {
        when (it) {
            is ScreenState.CurrentProductList -> it.shoppingList
            is ScreenState.ArchivedProductList -> it.shoppingList
            else -> null
        }
    }

    private val productList = selectedDietList.switchMap {
        dietListRepository.getProductList(it.id)
    }

    private val productListUi = mapNonNull(productList) {
        when (it) {
            is ResultStatus.Loading -> ResultStatus.Loading
            is ResultStatus.Success -> ResultStatus.Success(it.data.asUiModel())
            is ResultStatus.Error -> it
        }
    }

    fun createDietList(name: String) {
        _createDietListLoading.value = true
        val dietList = DietList(shoppingListName = name)

        viewModelScope.launch {
            dietListRepository.insertShoppingList(dietList)
            _createDietListLoading.value = false
        }
    }

    fun updateDietList(dietList: DietListUi, isArchived: Boolean) {
        _updateDietListLoading.value = true
        val dietListDomain = dietList.copy(isArchived = isArchived).asDomainModel()
        viewModelScope.launch {
            dietListRepository.updateDietList(dietListDomain)
            _updateDietListLoading.value = false
        }
    }

    fun deleteDietList(dietList: DietListUi) {
        viewModelScope.launch {
            dietListRepository.deleteDietList(dietList.asDomainModel())
        }
    }

    fun createProduct(name: String, quantity: Long, dietListId: Long) {
        _createProductLoading.value = true

        val product = Product(
            productName = name,
            productQuantity = quantity,
            shoppingListId = dietListId
        )
        viewModelScope.launch {
            dietListRepository.insertProduct(product)
            _createProductLoading.value = false
        }
    }

    fun editProduct(product: ProductUi, newName: String, newQuantity: Long) {
        _createProductLoading.value = true

        val updatedProduct = product.copy(
            productName = newName,
            productQuantity = newQuantity
        )
        viewModelScope.launch {
            dietListRepository.updateProduct(updatedProduct.asDomainModel())
            _createProductLoading.value = false
        }
    }

    fun deleteProduct(product: ProductUi) {
        _deleteProductLoading.value = true
        viewModelScope.launch {
            dietListRepository.deleteProduct(product.asDomainModel())
            _deleteProductLoading.value = false
        }
    }

    private val _screenState = MediatorLiveData<MutableScreenUi>().apply {
        value = MutableScreenUi(ScreenState.CurrentDietList)
        addSource(currentScreenState) { value?.currentScreenState = it ?: return@addSource }
        addSource(selectedDietList) { value?.selectedShoppingList = it }
        addSourceInvoke(createDietListLoading) { value?.createShoppingListLoading = it }
        addSourceInvoke(updateDietListLoading) { value?.updateShoppingListLoading = it }
        addSourceInvoke(createProductLoading) { value?.createProductLoading = it }
        addSourceInvoke(deleteProductLoading) { value?.deleteProductLoading = it }
        addSourceInvoke(dietListsUi) { value?.shoppingListsUi = it }
        addSourceInvoke(productListUi) { value?.productListUi = it }
    }
    val screenState: LiveData<ScreenUi> = _screenState.map { it.copy() }
}
