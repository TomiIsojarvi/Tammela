package com.example.tammela.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.tammela.data.model.ShoppingItem

@Composable
fun DeleteItemDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    items: List<ShoppingItem>,
) {
    if (items.size > 1) {
        AlertDialog(
            title = {
                Text(text = "Varmennus")
            },
            text = {
                Text(
                    text = "Haluatko poistaa ${items.size} tuotetta?"
                )
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text("KYLLÄ")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("EI")
                }
            }
        )
    } else {
        AlertDialog(
            title = {
                Text(text = "Varmennus")
            },
            text = {
                Text(
                    text = "Haluatko poistaa tuoteen: ${items.first().item}?"
                )
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text("KYLLÄ")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("EI")
                }
            }
        )
    }
}
