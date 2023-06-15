package com.metropolitan.cs330_project.ui.view.layout.archivedList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
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
import com.metropolitan.cs330_project.ui.model.model.DietListUi
import com.metropolitan.cs330_project.ui.model.state.ScreenState
import com.metropolitan.cs330_project.util.getDateFormat

@Composable
fun DietListArchivedItem(
    sharedViewModel: SharedViewModel,
    post: DietListUi
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp,
            modifier = Modifier.clickable(onClick = {
                sharedViewModel.pushBackStack(ScreenState.ArchivedProductList(post))
            })
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
            ) {
                Text(
                    text = post.shoppingListName,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(8.dp)
                )
                Column(horizontalAlignment = Alignment.End) {
                    IconButton(onClick = { sharedViewModel.updateDietList(post, false) }) {
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_unarchive_black_24dp),
                            "",
                            Modifier.wrapContentSize()
                        )
                    }
                    Text(
                        text = post.shoppingListTimestamp.getDateFormat(),
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(end = 8.dp),
                        style = TextStyle(fontSize = 16.sp, fontStyle = FontStyle.Italic)
                    )
                    IconButton(onClick = { sharedViewModel.deleteDietList(post) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                }
            }
        }
    }
}
