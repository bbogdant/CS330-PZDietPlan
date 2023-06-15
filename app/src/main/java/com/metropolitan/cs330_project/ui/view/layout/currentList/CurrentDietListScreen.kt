package com.metropolitan.cs330_project.ui.view.layout.currentList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.metropolitan.dietplan.R
import com.metropolitan.cs330_project.data.status.ResultStatus
import com.metropolitan.cs330_project.ui.model.AmbientScreenState
import com.metropolitan.cs330_project.ui.model.AmbientSharedViewModel
import com.metropolitan.cs330_project.ui.model.model.DietListUi
import com.metropolitan.cs330_project.ui.model.model.compose.CurrentDietListModel.showDialogState
import com.metropolitan.cs330_project.ui.view.layout.EmptyScreen
import com.metropolitan.cs330_project.ui.view.menu.AppDrawer
import com.metropolitan.cs330_project.ui.view.menu.MainMenu
import com.metropolitan.cs330_project.ui.theme.util.TestTag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DietListCurrentScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val screenState = AmbientScreenState.current.currentScreenState

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            AppDrawer(
                currentScreenState = screenState,
                closeDrawer = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            )
        },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                backgroundColor = MaterialTheme.colors.primary,
                navigationIcon = {
                    IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(ImageVector.vectorResource(R.drawable.ic_baseline_menu_24), "")
                    }
                },
                actions = { MainMenu() }
            )
        },
        content = { padding->
            ScreenBody(padding)
            CreateDietListDialog(padding)
        },
        floatingActionButton = { Fab() }
    )
}

@Composable
private fun ScreenBody(padding : PaddingValues) {
    when (val result = AmbientScreenState.current.shoppingListsUi) {
        is ResultStatus.Loading -> LoadingScreen()
        is ResultStatus.Success<*> -> SuccessScreenShopping(result.data as List<DietListUi>)
        is ResultStatus.Error -> ErrorScreen()
        else -> {}
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { CircularProgressIndicator() }
}

@Composable
private fun SuccessScreenShopping(productList: List<DietListUi>) {
    val sharedViewModel = AmbientSharedViewModel.current
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 8.dp, 96.dp)
            .verticalScroll(rememberScrollState())
    ) {
        productList.forEach { post -> DietListCurrentItem(sharedViewModel, post) }
    }
}

@Composable
private fun ErrorScreen() {
    EmptyScreen(
        R.string.empty_view_shopping_list_title,
        R.string.empty_view_shopping_list_subtitle
    )
}

@Composable
private fun Fab() {
    FloatingActionButton(
        modifier = Modifier.testTag(TestTag.fab),
        onClick = { showDialogState.value = true },
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = contentColorFor(MaterialTheme.colors.onSecondary),
        content = { Icon(ImageVector.vectorResource(R.drawable.ic_add_black_24dp), "") }
    )
}