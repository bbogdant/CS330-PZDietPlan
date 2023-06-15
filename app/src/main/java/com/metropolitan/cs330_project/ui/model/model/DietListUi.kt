package com.metropolitan.cs330_project.ui.model.model

data class DietListUi(
    val id: Long,
    val shoppingListName: String,
    val shoppingListTimestamp: Long,
    val isArchived: Boolean
)