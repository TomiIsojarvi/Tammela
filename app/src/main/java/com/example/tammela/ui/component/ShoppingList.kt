package com.example.tammela.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.data.model.ShoppingItem
import com.example.tammela.ui.viewmodel.ShoppingListViewModel

@Composable
fun ShoppingList(
    modifier: Modifier = Modifier,
    shoppingList: List<ShoppingItem>,
    onSelection: (Int) -> Unit,
    onEdit: (ShoppingItem) -> Unit,
) {
    LazyColumn {
        itemsIndexed(shoppingList) {index, item ->
            ShoppingItemCard(
                modifier = modifier,
                item = item,
                index = index,
                onSelection = onSelection,
                onEdit = onEdit,
                isSelected = item.isSelected)
        }
    }
}