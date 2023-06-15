package com.metropolitan.cs330_project.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.metropolitan.cs330_project.data.database.model.Product
import com.metropolitan.cs330_project.data.database.model.DietList

@Dao
interface DietListDatabaseDao {

    @Insert
    fun insertShoppingList(dietList: DietList)

    @Update
    fun updateShoppingList(dietList: DietList)

    @Insert
    fun insertProduct(product: Product)

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Delete
    fun deleteShoppingList(dietList: DietList)

    @Query("SELECT * FROM shopping_list WHERE isArchived IS 0 ORDER BY shoppingListTimestamp DESC")
    fun getCurrentShoppingList(): LiveData<List<DietList>>

    @Query("SELECT * FROM shopping_list WHERE isArchived IS 1 ORDER BY shoppingListTimestamp DESC")
    fun getArchivedShoppingList(): LiveData<List<DietList>>

    @Query("SELECT * FROM products WHERE shoppingListId = :listId ORDER BY productTimestamp DESC")
    fun getProductList(listId: Long): LiveData<List<Product>>
}
