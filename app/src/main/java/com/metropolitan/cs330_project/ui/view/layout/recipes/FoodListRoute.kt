package com.metropolitan.cs330_project.ui.view.layout.recipes

sealed class FoodListRoute(val route: String)  {
    object Home : FoodListRoute(route = "home")
    object FoodDetailScreen: FoodListRoute(route = "detail/{elementId}"){
        fun createRoute(elementId: Long) = "detail/$elementId"
    }
}