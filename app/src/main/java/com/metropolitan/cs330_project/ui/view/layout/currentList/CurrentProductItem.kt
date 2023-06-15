package com.metropolitan.cs330_project.ui.view.layout.currentList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metropolitan.dietplan.R
import com.metropolitan.cs330_project.ui.model.SharedViewModel
import com.metropolitan.cs330_project.ui.model.model.ProductUi
import com.metropolitan.cs330_project.util.getDateFormat

@Composable
fun ProductCurrentItem(
    sharedViewModel: SharedViewModel,
    product: ProductUi
) {
    var isEditing by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf(product.productName) }
    var newQuantity by remember { mutableStateOf(product.productQuantity.toString()) }

    LaunchedEffect(product) {
        newName = product.productName
        newQuantity = product.productQuantity.toString()
    }



    Row(modifier = Modifier.fillMaxWidth().padding(all = 8.dp)) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp,
            modifier = Modifier.fillMaxWidth().clickable(onClick = {
                isEditing = !isEditing
            })
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(all = 8.dp)) {
                if (isEditing) {
                    TextField(
                        value = newName,
                        onValueChange = { newName = it },
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                            .padding(8.dp)
                    )
                    TextField(
                        value = newQuantity,
                        onValueChange = { newQuantity = it },
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                            .padding(8.dp)
                    )
                } else {
                    Text(
                        text = newName,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                            .padding(8.dp)
                    )
                    Text(
                        text = newQuantity,
                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                            .padding(8.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    IconButton(onClick = { sharedViewModel.deleteProduct(product) }) {
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_delete_forever_black_24dp),
                            "",
                            Modifier.wrapContentSize()
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    if (isEditing) {
                        Button(
                            onClick = {
                                sharedViewModel.editProduct(
                                    product,
                                    newName = newName,
                                    newQuantity = newQuantity.toLongOrNull() ?: 0L
                                )
                                isEditing = false
                            }
                        ) {
                            Text("Save")
                        }
                    } else {
                        IconButton(onClick = {
                            isEditing = true
                        }) {
                            Icon(
                                ImageVector.vectorResource(com.google.android.material.R.drawable.material_ic_edit_black_24dp),
                                "",
                                Modifier.wrapContentSize()
                            )
                        }
                    }
                }
                Text(
                    text = product.productTimestamp.getDateFormat(),
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(end = 8.dp),
                    style = TextStyle(fontSize = 16.sp, fontStyle = FontStyle.Italic)
                )
            }
        }
    }
}


