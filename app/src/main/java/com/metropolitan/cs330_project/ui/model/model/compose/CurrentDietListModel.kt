package com.metropolitan.cs330_project.ui.model.model.compose

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange

object CurrentDietListModel {
    var showDialogState = mutableStateOf(false)
    var shoppingListNameState = mutableStateOf("")
    var editTextSelectionState = mutableStateOf(TextRange(0, 0))
}