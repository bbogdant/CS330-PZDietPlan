package com.metropolitan.cs330_project.ui.view.layout.currentList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metropolitan.dietplan.R
import com.metropolitan.cs330_project.ui.model.AmbientSharedViewModel
import com.metropolitan.cs330_project.ui.model.SharedViewModel
import com.metropolitan.cs330_project.ui.model.model.compose.CurrentDietListModel.editTextSelectionState
import com.metropolitan.cs330_project.ui.model.model.compose.CurrentDietListModel.shoppingListNameState
import com.metropolitan.cs330_project.ui.model.model.compose.CurrentDietListModel.showDialogState
import com.metropolitan.cs330_project.ui.theme.util.TestTag

@Composable
fun CreateDietListDialog(padding : PaddingValues) {
    val sharedViewModel = AmbientSharedViewModel.current
    if (showDialogState.value.not()) return

    AlertDialog(
        onDismissRequest = { showDialogState.value = false },
        title = {
            Text(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp),
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                text = stringResource(id = R.string.dialog_title_add_new_shopping_list)
            )
        },
        text = { EditText() },
        buttons = { DialogButtons(sharedViewModel) }
    )
}

@Composable
private fun DialogButtons(sharedViewModel: SharedViewModel) {
    Box(Modifier.fillMaxWidth().padding(all = 8.dp), contentAlignment = Alignment.CenterEnd) {
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            Spacer(Modifier.weight(0.3f, true))
            Button(
                modifier = Modifier.weight(0.35f, true).testTag(TestTag.dialogCancel),
                onClick = { showDialogState.value = false },
                border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                colors = ButtonDefaults.outlinedButtonColors(),
                content = {
                    Text(
                        text = stringResource(R.string.dialog_button_cancel),
                        maxLines = 1,
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            )
            Spacer(Modifier.width(8.dp))
            Button(
                modifier = Modifier.weight(0.35f, true).testTag(TestTag.dialogAdd),
                onClick = {
                    if (shoppingListNameState.value.isEmpty().not()) {
                        sharedViewModel.createDietList(shoppingListNameState.value)
                        showDialogState.value = false
                        shoppingListNameState.value = ""
                    }
                },
                content = {
                    Text(
                        text = stringResource(R.string.dialog_button_add),
                        maxLines = 1,
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            )
        }
    }
}

@Composable
private fun EditText() {
    Column {
        Spacer(Modifier.height(16.dp))
        Box {
            TextField(
                value = TextFieldValue(shoppingListNameState.value, editTextSelectionState.value),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                textStyle = TextStyle(fontSize = 18.sp),
                onValueChange = {
                    shoppingListNameState.value = it.text
                    editTextSelectionState.value = TextRange(it.text.length, it.text.length)
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor =  MaterialTheme.colors.surface
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.dialog_enter_shopping_list_name),
                        color = Color.Gray,
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
            )
        }
    }
}