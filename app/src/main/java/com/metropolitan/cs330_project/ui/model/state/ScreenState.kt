package com.metropolitan.cs330_project.ui.model.state

import com.metropolitan.cs330_project.ui.model.model.DietListUi


sealed class ScreenState {
    object CurrentDietList : ScreenState()
    object ArchivedDietList : ScreenState()
    class CurrentProductList(val shoppingList: DietListUi) : ScreenState()
    class ArchivedProductList(val shoppingList: DietListUi) : ScreenState()
    object Empty : ScreenState()
    object HomeScreen : ScreenState()




}