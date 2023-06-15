package com.metropolitan.cs330_project.ui.view.menu

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.metropolitan.cs330_project.ui.model.AmbientSharedViewModel
import com.metropolitan.cs330_project.ui.model.model.compose.MainMenuModel.screenStateState
import com.metropolitan.cs330_project.ui.model.model.compose.MainMenuModel.showMenu
import com.metropolitan.cs330_project.ui.model.state.ScreenState
import com.metropolitan.dietplan.R

@Composable
fun MainMenu() {
    val sharedViewModel = AmbientSharedViewModel.current

    IconButton(onClick = { showMenu.value = true }) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_more_vert_black_24),
            contentDescription = "",
        )
    }
    if (showMenu.value.not()) {
        if (screenStateState.value == ScreenState.Empty) return
        screenStateState.value.let { sharedViewModel.pushBackStack(it) }
        screenStateState.value = ScreenState.Empty
        return
    }
    DropdownMenu(
        expanded = showMenu.value,
        onDismissRequest = { showMenu.value = false }
    ) {
        DropdownMenuItem(onClick = {
            screenStateState.value = ScreenState.CurrentDietList
            showMenu.value = false
        }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = stringResource(id = R.string.menu_current_shopping_list),
                color = MaterialTheme.colors.onSurface,
                maxLines = 1
            )
        }
        DropdownMenuItem(onClick = {
            screenStateState.value = ScreenState.ArchivedDietList
            showMenu.value = false
        }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = stringResource(id = R.string.menu_archived_shopping_list),
                color = MaterialTheme.colors.onSurface,
                maxLines = 1
            )
        }
    }
}