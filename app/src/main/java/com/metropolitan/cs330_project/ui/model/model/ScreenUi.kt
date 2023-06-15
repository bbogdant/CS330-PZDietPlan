package com.metropolitan.cs330_project.ui.model.model

import com.metropolitan.cs330_project.data.status.ResultStatus
import com.metropolitan.cs330_project.ui.model.state.ScreenState

data class MutableScreenUi(
    override var currentScreenState: ScreenState,
    override var selectedShoppingList: DietListUi? = null,
    override var updateShoppingListLoading: Boolean? = null,
    override var deleteProductLoading: Boolean? = null,
    override var createProductLoading: Boolean? = null,
    override var createShoppingListLoading: Boolean? = null,
    override var shoppingListsUi: ResultStatus<List<DietListUi>>? = null,
    override var productListUi: ResultStatus<List<ProductUi>>? = null
) : ScreenUi(currentScreenState)

abstract class ScreenUi(
    open val currentScreenState: ScreenState,
    open val selectedShoppingList: DietListUi? = null,
    open val updateShoppingListLoading: Boolean? = null,
    open val deleteProductLoading: Boolean? = null,
    open val createProductLoading: Boolean? = null,
    open val createShoppingListLoading: Boolean? = null,
    open val shoppingListsUi: ResultStatus<List<DietListUi>>? = null,
    open val productListUi: ResultStatus<List<ProductUi>>? = null
)