package com.example.tammela.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteItemDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
//    dialogTitle: String,
    item: String,
//    icon: ImageVector,
) {
    AlertDialog(
        title = {
            Text(text = "Varmennus")
        },
        text = {
            Text(
                text = "Haluatko poistaa tuoteen $item?"
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
