package com.metropolitan.cs330_project.data.database

import androidx.lifecycle.LiveData
import com.metropolitan.cs330_project.data.database.model.Product
import com.metropolitan.cs330_project.data.database.model.DietList
import com.metropolitan.cs330_project.data.status.ResultStatus

interface DietListSource {
    suspend fun insertShoppingList(dietList: DietList)

    suspend fun updateShoppingList(dietList: DietList)

    suspend fun deleteShoppingList(dietList: DietList)

    suspend fun insertProduct(product: Product)

    suspend fun updateProduct(product: Product)

    suspend fun deleteProduct(product: Product)

    fun getCurrentShoppingList(): LiveData<ResultStatus<List<DietList>>>

    fun getArchivedShoppingList(): LiveData<ResultStatus<List<DietList>>>

    fun getProductList(listId: Long): LiveData<ResultStatus<List<Product>>>
}