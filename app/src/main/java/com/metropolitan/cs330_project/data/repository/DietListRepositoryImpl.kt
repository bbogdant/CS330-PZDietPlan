package com.metropolitan.cs330_project.data.repository

import com.metropolitan.cs330_project.data.database.model.Product
import com.metropolitan.cs330_project.data.database.model.DietList
import com.metropolitan.cs330_project.data.database.DietListSource
import com.metropolitan.cs330_project.data.repository.DietListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DietListRepositoryImpl constructor(
    private val dietListSource: DietListSource,
    private val ioDispatcher: CoroutineDispatcher
) : DietListRepository {

    override suspend fun insertShoppingList(dietList: DietList) {
        withContext(ioDispatcher) {
            dietListSource.insertShoppingList(dietList)
        }
    }

    override suspend fun updateDietList(dietList: DietList) {
        withContext(ioDispatcher) {
            dietListSource.updateShoppingList(dietList)
        }
    }

    override suspend fun insertProduct(product: Product) {
        withContext(ioDispatcher) {
            dietListSource.insertProduct(product)
        }
    }

    override suspend fun updateProduct(product: Product) {
        withContext(ioDispatcher) {
            dietListSource.updateProduct(product)
        }
    }

    override suspend fun deleteProduct(product: Product) {
        withContext(ioDispatcher) {
            dietListSource.deleteProduct(product)
        }
    }

    override suspend fun deleteDietList(dietList: DietList) {
        withContext(ioDispatcher) {
            dietListSource.deleteShoppingList(dietList)
        }
    }

    override fun getCurrentShoppingList() = dietListSource.getCurrentShoppingList()

    override fun getArchivedShoppingList() = dietListSource.getArchivedShoppingList()

    override fun getProductList(listId: Long) = dietListSource.getProductList(listId)
}