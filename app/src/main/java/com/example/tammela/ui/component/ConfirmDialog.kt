package com.example.tammela.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ConfirmDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
//    dialogTitle: String,
    command: String,
//    icon: ImageVector,
) {
    AlertDialog(
        /*icon = {
            Icon(icon, contentDescription = "Example Icon")
        },*/
        title = {
            Text(text = "Varmennus")
        },
        text = {
            Text(
                text = "Haluatko lähettää viestin $command?"
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
