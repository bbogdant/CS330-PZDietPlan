package com.metropolitan.cs330_project.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.metropolitan.cs330_project.data.database.model.Product
import com.metropolitan.cs330_project.data.database.model.DietList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.metropolitan.cs330_project.data.status.ResultStatus
import java.lang.Exception

class DietListSourceImpl constructor(
    private val dietListDatabase: DietListDatabaseDao,
    private val ioDispatcher: CoroutineDispatcher
) : DietListSource {

    override suspend fun insertShoppingList(dietList: DietList) {
        withContext(ioDispatcher) {
            dietListDatabase.insertShoppingList(dietList)
        }
    }

    override suspend fun updateShoppingList(dietList: DietList) {
        withContext(ioDispatcher) {
            dietListDatabase.updateShoppingList(dietList)
        }
    }

    override suspend fun insertProduct(product: Product) {
        withContext(ioDispatcher) {
            dietListDatabase.insertProduct(product)
        }
    }

    override suspend fun updateProduct(product: Product) {
        withContext(ioDispatcher) {
            dietListDatabase.updateProduct(product)
        }
    }

    override suspend fun deleteShoppingList(dietList: DietList) {
        withContext(ioDispatcher) {
            dietListDatabase.deleteShoppingList(dietList)
        }
    }

    override suspend fun deleteProduct(product: Product) {
        withContext(ioDispatcher) {
            dietListDatabase.deleteProduct(product)
        }
    }

    override fun getCurrentShoppingList(): LiveData<ResultStatus<List<DietList>>> {
        return dietListDatabase.getCurrentShoppingList().map {
            if (it.isNotEmpty()) ResultStatus.Success(it) else ResultStatus.Error(Exception("ShoppingList not found!"))
        }
    }

    override fun getArchivedShoppingList(): LiveData<ResultStatus<List<DietList>>> {
        return dietListDatabase.getArchivedShoppingList().map {
            if (it.isNotEmpty()) ResultStatus.Success(it) else ResultStatus.Error(Exception("ShoppingList not found!"))
        }
    }

    override fun getProductList(listId: Long): LiveData<ResultStatus<List<Product>>> {
        return dietListDatabase.getProductList(listId).map {
            if (it.isNotEmpty()) ResultStatus.Success(it) else ResultStatus.Error(Exception("Product not found!"))
        }
    }
}