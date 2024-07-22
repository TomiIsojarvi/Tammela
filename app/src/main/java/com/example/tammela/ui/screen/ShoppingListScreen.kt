package com.example.tammela.ui.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.ui.component.AddItemDialog
import com.example.tammela.ui.component.ConfirmDialog
import com.example.tammela.ui.component.SensorCardList
import com.example.tammela.ui.component.ShoppingList
import com.example.tammela.ui.viewmodel.SensorViewModel
import com.example.tammela.ui.viewmodel.SettingsViewModel
import com.example.tammela.ui.viewmodel.ShoppingListViewModel
import kotlinx.coroutines.launch

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
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column (modifier.fillMaxSize()){
            Button(
                onClick = {
                    showAddItemDialog = true
                    //viewModel.clearSensorData()
                    //viewModel.refreshSensorData()
                },
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray.copy(alpha = 0.2f),
                    contentColor = Color.Black)
            )
            {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text("Lisää")
            }
            ShoppingList(viewModel, modifier)
        }
    }

    if (showAddItemDialog) {
        AddItemDialog(
            onDismissRequest = {
                showAddItemDialog = false
                //Toast.makeText(context, "Tuotetta ei lisätty", Toast.LENGTH_SHORT).show()
            },
            onConfirmation = { newItem ->
                viewModel.addItemToShoppingList(settingsViewModel.username, newItem)
                showAddItemDialog = false
            },
        )
    }
}