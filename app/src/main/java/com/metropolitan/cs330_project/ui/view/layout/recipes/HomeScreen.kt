package com.metropolitan.cs330_project.ui.view.layout.recipes

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

@Composable
fun HomeScreen(vm: FoodViewModel) {

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        vm.granted.value = isGranted
    }

    Column {
        if (!vm.granted.value) {
            InternetPermission(launcher)
        } else {
            ListFoods(vm = vm)
        }
    }
}

@Composable
private fun InternetPermission(launcher: ManagedActivityResultLauncher<String, Boolean>) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "WELCOME",
                style = MaterialTheme.typography.h5,
                color = Color.Magenta,
                modifier = Modifier.padding(8.dp)
            )
            Button(onClick = { launcher.launch(Manifest.permission.INTERNET) }) {
                Text("Get Started")
            }
        }
    }
}

@Composable
fun ListFoods(vm: FoodViewModel) {
    LazyColumn {
        items(vm.foods) { food ->
            PresentFood(food = food) {
                vm.navigateToFoodDetails(it)
            }
        }
    }
}

@Composable
fun PresentFood(food: Food, onFoodSelected: (Long) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable{onFoodSelected(food.id)}) {
        AsyncImage(
            model = food.image,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Column {
            Text(text = "${food.name}",
                fontSize = 18.sp)
            Text(text = "Number of servings: ${food.number_of_servings}", color = Color.Gray,
                modifier = Modifier.padding(all = 4.dp))
        }
        Spacer(Modifier.weight(1f))
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(end = 8.dp)) {
            Text(text = food.difficulty_in_making, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview
@Composable
fun InternetPermissionPreview() {
    InternetPermission(rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { })
}

@Preview
@Composable
fun ListFoodsPreview() {
    val vm: FoodViewModel = viewModel()
    vm.navController = rememberNavController()
    ListFoods(vm)
}