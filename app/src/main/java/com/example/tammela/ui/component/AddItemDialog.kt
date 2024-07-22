package com.example.tammela.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.ui.viewmodel.SettingsViewModel
import com.example.tammela.ui.viewmodel.ShoppingListViewModel

/*
@Composable
fun AddItemDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    var text by remember { mutableStateOf("") }
    val viewModel: ShoppingListViewModel = viewModel()

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column {
                Column(Modifier.padding(24.dp)) {
                    Text(
                        text = "Lisää puutelistaan",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Tuote") },
                    )
                }
                Spacer(Modifier.height(4.dp))
                Row(
                    Modifier.padding(8.dp).fillMaxWidth(),
                    Arrangement.spacedBy(8.dp, Alignment.End),
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        content = { Text("PERUUTA") },
                    )
                    TextButton(
                        onClick = { onConfirmation() },
                        content = { Text("LISÄÄ") },
                    )
                }
            }
        }
    }
}*/

@Composable
fun AddItemDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    val viewModel: ShoppingListViewModel = viewModel()

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column {
                Column(Modifier.padding(24.dp)) {
                    Text(
                        text = "Lisää puutelistaan",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Tuote") },
                    )
                }
                Spacer(Modifier.height(4.dp))
                Row(
                    Modifier.padding(8.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        content = { Text("PERUUTA") },
                    )
                    TextButton(
                        onClick = { onConfirmation(text) },
                        content = { Text("LISÄÄ") },
                    )
                }
            }
        }
    }
}
