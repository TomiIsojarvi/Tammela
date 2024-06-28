package com.example.tammela.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.ui.viewmodel.SensorViewModel
import com.example.tammela.ui.viewmodel.ShoppingListViewModel

@Composable
fun ShoppingList(
    viewModel: ShoppingListViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val shoppingList by viewModel.shoppingList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getShoppingList()
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(shoppingList ?: emptyArray()) { item ->
                ShoppingItemCard(item = item)
            }
        }
    }
}