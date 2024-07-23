package com.example.tammela.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.ui.viewmodel.ShoppingListViewModel

@Composable
fun EditItemDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    oldItem: String
) {
    var text by remember { mutableStateOf(oldItem) }
    val viewModel: ShoppingListViewModel = viewModel()

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column {
                Column(Modifier.padding(24.dp)) {
                    Text(
                        text = "Muokkaa",
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
                        content = { Text("MUOKKAA") },
                    )
                }
            }
        }
    }
}
