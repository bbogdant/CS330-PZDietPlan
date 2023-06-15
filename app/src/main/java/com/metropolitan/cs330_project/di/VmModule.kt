package com.metropolitan.cs330_project.di

import android.content.Context
import com.metropolitan.cs330_project.data.database.DietListDatabase
import com.metropolitan.cs330_project.data.database.DietListDatabaseDao
import com.metropolitan.cs330_project.data.database.DietListSource
import com.metropolitan.cs330_project.data.database.DietListSourceImpl
import com.metropolitan.cs330_project.data.repository.DietListRepository
import com.metropolitan.cs330_project.data.repository.DietListRepositoryImpl
import com.metropolitan.cs330_project.ui.model.ScreenBackStack
import com.metropolitan.cs330_project.ui.model.ScreenBackStackImpl
import com.metropolitan.cs330_project.ui.model.SharedViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiDataModule = module {
    viewModel { SharedViewModel(get(), get()) }
    single { createBackStack() }
}

val dataModule = module {
    single { createShoppingListSource(get(), get()) }
    single { createRepository(get(), get()) }
    single { createDatabase(get()) }
    single { createIoDispatcher() }
}

fun createBackStack(): ScreenBackStack = ScreenBackStackImpl()

fun createIoDispatcher() = Dispatchers.Default

fun createDatabase(context: Context): DietListDatabaseDao {
    return DietListDatabase.getInstance(context).dietListDatabaseDao
}

fun createRepository(
    shoppingListDatabase: DietListSource,
    ioDispatcher: CoroutineDispatcher
): DietListRepository {
    return DietListRepositoryImpl(shoppingListDatabase, ioDispatcher)
}

fun createShoppingListSource(
    shoppingListDatabase: DietListDatabaseDao,
    ioDispatcher: CoroutineDispatcher
): DietListSource {
    return DietListSourceImpl(shoppingListDatabase, ioDispatcher)
}