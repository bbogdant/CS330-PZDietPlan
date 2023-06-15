package com.metropolitan.cs330_project.ui.view.layout.recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

@Composable
fun FoodDetailScreen(vm: FoodViewModel, foodId: Long) {
    FoodDetailScreenContent(vm.getFood(foodId)){
        vm.goBack()
    }
}

@Composable
fun FoodDetailScreenContent(food: Food?, goBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                    IconButton(
                        modifier = Modifier.background(Color.Transparent),
                        onClick = goBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
            item {
                AsyncImage(
                    model = food?.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(240.dp)
                        .clip(CircleShape)
                )
            }
            item {
                FoodBasicData(food = food)
            }
            item {
                FoodPersonalData(food = food)
            }
        }
    }
}

@Composable
fun RowInCard(leftText: String, rightText: String){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = leftText,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = rightText,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun FoodPersonalData(food: Food?){
    Card(elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(16.dp)){
        Column(modifier = Modifier.padding(8.dp)) {
            RowInCard(leftText = "Ingredients:", rightText = "-----------------------")
            food?.ingredients?.forEach {
                RowInCard(leftText = "Ingredient name: ", rightText = "${it?.name}")
                RowInCard(leftText = "Ingredient quality: ", rightText = "${it?.food_quality}")
                RowInCard(leftText = "Ingredient quantity: ", rightText = "${it?.quantity}")
                RowInCard(leftText = "", rightText = "-----------------------")
            }
        }
    }
}

@Composable
fun FoodBasicData(food: Food?){
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            RowInCard(leftText = "Name: ", rightText ="${food?.name}")
            RowInCard(leftText = "Difficulty: ", rightText = "${food?.difficulty_in_making}")
            RowInCard(leftText = "Number of servings: ", rightText = "${food?.number_of_servings}")
            RowInCard(leftText = "Time to make: ", rightText ="${food?.time_to_make}h")
            RowInCard(leftText = "Description: ", rightText = "${food?.description}")
        }
    }
}

@Preview
@Composable
fun FoodDetailScreenPreview() {
    val vm: FoodViewModel = viewModel()
    vm.navController = rememberNavController()
    FoodDetailScreen(vm, foodId = 1)
}