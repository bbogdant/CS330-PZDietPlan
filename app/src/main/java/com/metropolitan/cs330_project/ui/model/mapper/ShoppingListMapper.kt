package com.metropolitan.cs330_project.ui.model.mapper

import com.metropolitan.cs330_project.data.database.model.DietList
import com.metropolitan.cs330_project.ui.model.model.DietListUi

fun List<DietList>.asUiModel(): List<DietListUi> {
    return map { it.asUiModel() }
}

fun DietList.asUiModel(): DietListUi {
    return DietListUi(
        id = id,
        shoppingListName = shoppingListName,
        shoppingListTimestamp = shoppingListTimestamp,
        isArchived = isArchived
    )
}

fun DietListUi.asDomainModel(): DietList {
    return DietList(
        id = id,
        shoppingListName = shoppingListName,
        shoppingListTimestamp = shoppingListTimestamp,
        isArchived = isArchived
    )
}