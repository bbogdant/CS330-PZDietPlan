package com.metropolitan.cs330_project.ui.view.layout.recipes

import android.content.Context
import java.io.BufferedReader
import com.beust.klaxon.Klaxon

private val klaxon = Klaxon()

data class FoodsModel(val foods: List<Food>) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<FoodsModel>(json)

        fun readAsset(context: Context, fileName: String): String =
            context
                .assets
                .open(fileName)
                .bufferedReader()
                .use(BufferedReader::readText)
    }
}

data class Food (
    val id: Long,
    val name: String,
    val image: String,
    val number_of_servings: Int,
    val description: String,
    val time_to_make: Double,
    val ingredients: List<Ingredient>,
    val difficulty_in_making: String
)

data class Ingredient (
    val name: String,
    val food_quality: String,
    val quantity: Int
)