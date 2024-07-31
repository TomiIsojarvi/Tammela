package com.example.tammela.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tammela.data.model.ShoppingItem

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