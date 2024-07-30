package com.example.tammela.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.data.model.ShoppingItem
import com.example.tammela.ui.component.AddItemDialog
import com.example.tammela.ui.component.DeleteItemDialog
import com.example.tammela.ui.component.EditItemDialog
import com.example.tammela.ui.component.ShoppingList
import com.example.tammela.ui.viewmodel.SettingsViewModel
import com.example.tammela.ui.viewmodel.ShoppingListViewModel
import kotlinx.coroutines.launch

/*
@Composable
fun ShoppingListScreen(
    context: Context,
    modifier: Modifier = Modifier
) {
    val viewModel: ShoppingListViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val settingsViewModel: SettingsViewModel = viewModel()
    var showAddItemDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            settingsViewModel.loadUserData(context)
            viewModel.getShoppingList()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier.fillMaxSize()) {
            Row {
                Button(
                    enabled = false,
                    onClick = {
                        //showAddItemDialog = true
                    },
                    modifier = Modifier
                        //.align(alignment = Alignment.End)
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray.copy(alpha = 0.2f),
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Poista")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        showAddItemDialog = true
                    },
                    modifier = Modifier
                        //.align(alignment = Alignment.End)
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray.copy(alpha = 0.2f),
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Lisää")
                }
            }
            ShoppingList(modifier, viewModel.shoppingList, {}, {})
        }
    }

    if (showAddItemDialog) {
        AddItemDialog(
            onDismissRequest = {
                showAddItemDialog = false
            },
            onConfirmation = { newItem ->
                viewModel.addItemToShoppingList(settingsViewModel.username, newItem)
                showAddItemDialog = false
            },
        )
    }
}
*/

@Composable
fun ShoppingListScreen(
    context: Context,
    modifier: Modifier = Modifier
) {
    val viewModel: ShoppingListViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val settingsViewModel: SettingsViewModel = viewModel()

    var showAddItemDialog by remember { mutableStateOf(false) }
    var showDeleteItemDialog by remember { mutableStateOf( false ) }
    var showEditItemDialog by remember { mutableStateOf( false ) }
    var currentItemBeingEdited by remember { mutableStateOf<ShoppingItem?>(null) }

    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            settingsViewModel.loadUserData(context)
            viewModel.getShoppingList()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier.fillMaxSize()) {
            Row {
                Button(
                    enabled = viewModel.getSelectedItems().isNotEmpty(),
                    onClick = { showDeleteItemDialog = true },
                    modifier = Modifier
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray.copy(alpha = 0.2f),
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Poista")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        showAddItemDialog = true
                    },
                    modifier = Modifier
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray.copy(alpha = 0.2f),
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Lisää")
                }
            }
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                ShoppingList(
                    modifier = modifier,
                    shoppingList = viewModel.shoppingList,
                    onSelection = { index: Int -> viewModel.shoppingList[index].toggleSelection() },
                    onEdit = { item ->
                        currentItemBeingEdited = item
                        showEditItemDialog = true
                    }
                )
            }
        }
    }

    if (showAddItemDialog) {
        AddItemDialog(
            onDismissRequest = {
                showAddItemDialog = false
            },
            onConfirmation = { newItem ->
                viewModel.addItemToShoppingList(settingsViewModel.username, newItem)
                showAddItemDialog = false
            },
        )
    }

    if (showEditItemDialog) {
        currentItemBeingEdited?.let { item ->
            EditItemDialog(
                onDismissRequest = {
                    showEditItemDialog = false
                    currentItemBeingEdited = null
                },
                onConfirmation = { editedItem ->
                    viewModel.editItemFromShoppingList(settingsViewModel.username, item.rowId, itemDesc = editedItem)
                    showEditItemDialog = false
                    currentItemBeingEdited = null
                },
                oldItem = item.item
            )
        }
    }

    if (showDeleteItemDialog) {
        val deletedItems: List<ShoppingItem> = viewModel.getSelectedItems()

        DeleteItemDialog(
            onDismissRequest = {
                showDeleteItemDialog = false
            },
            onConfirmation = {
                viewModel.deleteItemsFromShoppingList(settingsViewModel.username, deletedItems)
                showDeleteItemDialog = false
            },
            items = deletedItems
        )
    }
}
