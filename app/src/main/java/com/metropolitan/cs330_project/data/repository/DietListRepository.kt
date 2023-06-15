package com.metropolitan.cs330_project.data.repository

import androidx.lifecycle.LiveData
import com.metropolitan.cs330_project.data.database.model.Product
import com.metropolitan.cs330_project.data.database.model.DietList
import com.metropolitan.cs330_project.data.status.ResultStatus

interface DietListRepository {
    suspend fun insertShoppingList(dietList: DietList)

    suspend fun updateDietList(dietList: DietList)

    suspend fun insertProduct(product: Product)

    suspend fun updateProduct(product: Product)

    suspend fun deleteProduct(product: Product)

    suspend fun deleteDietList(dietList: DietList)

    fun getCurrentShoppingList(): LiveData<ResultStatus<List<DietList>>>

    fun getArchivedShoppingList(): LiveData<ResultStatus<List<DietList>>>

    fun getProductList(listId: Long): LiveData<ResultStatus<List<Product>>>
}