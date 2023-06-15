package com.metropolitan.cs330_project.ui.view.layout


import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import com.metropolitan.cs330_project.ui.model.AmbientScreenState
import com.metropolitan.cs330_project.ui.model.AmbientSharedViewModel
import com.metropolitan.cs330_project.ui.model.model.ScreenUi
import com.metropolitan.cs330_project.ui.model.state.ScreenState
import com.metropolitan.cs330_project.ui.theme.ShoppingListTheme
import com.metropolitan.cs330_project.ui.view.layout.archivedList.ArchivedProductListScreen
import com.metropolitan.cs330_project.ui.view.layout.archivedList.DietListArchivedScreen
import com.metropolitan.cs330_project.ui.view.layout.currentList.ProductListCurrentScreen
import com.metropolitan.cs330_project.ui.view.layout.currentList.DietListCurrentScreen

@Composable
fun ShoppingListApp() {
    ShoppingListTheme {
        Surface(color = MaterialTheme.colors.background) {
            AppContent()
        }
    }
}

@Composable
private fun AppContent() {
    val sharedViewModelAmbient = AmbientSharedViewModel.current
    val screenState: State<ScreenUi?> = sharedViewModelAmbient.screenState.observeAsState()
    val screenStateValue = screenState.value
    screenStateValue ?: return

    CompositionLocalProvider(AmbientScreenState provides screenStateValue) {
        when (screenStateValue.currentScreenState) {
            is ScreenState.CurrentDietList -> DietListCurrentScreen()
            is ScreenState.ArchivedDietList -> DietListArchivedScreen()
            is ScreenState.CurrentProductList -> ProductListCurrentScreen()
            is ScreenState.ArchivedProductList -> ArchivedProductListScreen()

            else -> {}
        }
    }
}
