package com.example.tammela.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.data.model.ShoppingItem
import com.example.tammela.ui.viewmodel.SettingsViewModel
import com.example.tammela.ui.viewmodel.ShoppingListViewModel

/*
@Composable
fun ShoppingItemCard(
    item: ShoppingItem,
    modifier: Modifier = Modifier,
    onSelection: (Boolean) -> Unit
) {
    Surface(
        modifier = modifier.padding(10.dp),
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        shadowElevation = 10.dp
    ) {
        val viewModel: ShoppingListViewModel = viewModel()
        var showDeleteItemDialog by remember { mutableStateOf( false ) }
        var showEditItemDialog by remember { mutableStateOf( false ) }
        val settingsViewModel: SettingsViewModel = viewModel()

        Column (modifier = Modifier.fillMaxWidth())
        {
            Row (
                modifier = Modifier.padding(10.dp)
            )
            {
                Row(
                    modifier =  Modifier.weight(1f),
                )
                {
                    Text(
                        fontWeight = FontWeight(700),
                        text = item.user
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = item.createDate
                    )
                }
                */
/*IconButton(
                    modifier = Modifier
                        .height(20.dp)
                        .width(40.dp),
                    onClick = { },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Check the product"
                    )
                }*//*

                IconButton(
                    modifier = Modifier
                        .height(20.dp)
                        .width(40.dp),
                    onClick = { showEditItemDialog = true },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit the product"
                    )
                }
                */
/*IconButton(
                    modifier = Modifier
                        .height(20.dp)
                        .width(40.dp),
                    //enabled = buttonData.enabled,
                    onClick = { showDeleteItemDialog = true },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Delete the product"
                    )
                }*//*

            }
            Row (
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
            )
            {
                Text(
                    modifier =  Modifier.weight(1f),
                    //fontWeight = FontWeight(700),
                    text = item.item
                )
            }
        }

        if (showDeleteItemDialog) {
            DeleteItemDialog(
                onDismissRequest = {
                    showDeleteItemDialog = false
                },
                onConfirmation = {
                    viewModel.deleteItemFromShoppingList(settingsViewModel.username, item.rowId)
                    showDeleteItemDialog = false
                },
                item =item.item
            )
        }

        if (showEditItemDialog) {
            EditItemDialog(
                onDismissRequest = {
                    showEditItemDialog = false
                },
                onConfirmation = { editedItem ->
                    viewModel.editItemFromShoppingList(settingsViewModel.username, item.rowId, itemDesc = editedItem)
                    showEditItemDialog = false
                },
                oldItem = item.item
            )
        }
    }
}*/

@Composable
fun ShoppingItemCard(
    modifier: Modifier = Modifier,
    item: ShoppingItem,
    index: Int,
    onSelection: (Int) -> Unit,
    onEdit: (ShoppingItem) -> Unit,
    isSelected: Boolean
) {
    var backgroundColor: Color = MaterialTheme.colorScheme.background
    var borderColor: Color = MaterialTheme.colorScheme.secondary

    if (isSelected == true) {
        backgroundColor = Color.Green.copy(alpha = 0.2f)
        borderColor = Color.Green
    }

    Surface(
        modifier = modifier.padding(10.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        shadowElevation = 10.dp,
        onClick = { onSelection(index) }
    ) {
        Column (modifier = Modifier.fillMaxWidth())
        {
            Row (
                modifier = Modifier.padding(10.dp)
            )
            {
                Row(
                    modifier =  Modifier.weight(1f),
                )
                {
                    Text(
                        fontWeight = FontWeight(700),
                        text = item.user
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = item.createDate
                    )
                }
                IconButton(
                    modifier = Modifier
                        .height(20.dp)
                        .width(40.dp),
                    onClick = { onEdit(item) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit the product"
                    )
                }
            }
            Row (
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
            )
            {
                Text(
                    modifier =  Modifier.weight(1f),
                    text = item.item
                )
            }
        }
    }
}
