package com.metropolitan.cs330_project.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.metropolitan.cs330_project.data.database.model.Product
import com.metropolitan.cs330_project.data.database.model.DietList

@Database(entities = [DietList::class, Product::class], version = 2, exportSchema = false)
abstract class DietListDatabase : RoomDatabase() {

    abstract val dietListDatabaseDao: DietListDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: DietListDatabase? = null

        fun getInstance(context: Context): DietListDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DietListDatabase::class.java,
                        "shopping_list_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}