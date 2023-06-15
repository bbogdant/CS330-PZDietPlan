package com.metropolitan.cs330_project.ui.view.layout.recipes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import java.net.URL

class FoodViewModel() : ViewModel() {

    lateinit var navController: NavHostController
    private lateinit var foodList: MutableList<Food>
    var granted =  mutableStateOf(false)

    init {
        val jsonFile = loadResource("assets/food.json").readText()
        val model = FoodsModel.fromJson(jsonFile)
        model?.let {
            foodList = it.foods.toMutableStateList()
        }
    }

    fun loadResource(path: String): URL {
        println("Loading resource: $path")
        return Thread.currentThread().contextClassLoader.getResource(path)
    }

    val foods: List<Food>
        get() = foodList.sortedBy { it.id }

    fun getFood(id: Long): Food? {
        return foodList.find { it.id == id }
    }



    fun navigateToFoodDetails(id: Long) {
        navController.navigate(FoodListRoute.FoodDetailScreen.createRoute(id))
    }

    fun goBack() {
        navController.popBackStack()
    }
}