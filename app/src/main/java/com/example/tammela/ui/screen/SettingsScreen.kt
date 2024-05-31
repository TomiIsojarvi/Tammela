package com.example.tammela.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.ui.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "settings")

@Composable
fun TrailingIconButton(
    icon: ImageVector,
    contentDescription: String,
    tint: Color,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Composable
fun SettingsScreen(
    context: Context,
    modifier: Modifier = Modifier
) {
    val viewModel: SettingsViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    // Load initial data when the composable is first composed
    LaunchedEffect(Unit) {
        viewModel.loadUserData(context)
    }

    Column(
        Modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = modifier.padding(10.dp),
            fontWeight = FontWeight(700),
            style = MaterialTheme.typography.titleSmall,
            text = "Käyttäjätunnus:"
        )
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp, vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            value = viewModel.username,
            onValueChange = { viewModel.updateUsername(it) },
            label = { Text("Käyttäjätunnus") },
            singleLine = true,
            trailingIcon = {
                if (viewModel.username.isNotBlank()) {
                    TrailingIconButton(
                        icon = Icons.Default.Clear,
                        contentDescription = "Anna käyttäjätunnus",
                        tint = Color.Black,
                        onClick = { viewModel.updateUsername("") }
                    )
                }
            },
            supportingText = {
                if (viewModel.username.isEmpty()) {
                    Text(
                        text = "Syötä käyttäjätunnus",
                        color = Color.Red,
                    )
                }
            }
        )
        Text(
            modifier = modifier.padding(10.dp),
            fontWeight = FontWeight(700),
            style = MaterialTheme.typography.titleSmall,
            text = "GSM-numerot:"
        )
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp, vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            value = viewModel.remoteNumber,
            onValueChange = { viewModel.updateRemoteNumber(it) },
            label = { Text("Etäohjauksen GSM-numero") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            trailingIcon = {
                if (viewModel.remoteNumber.isNotBlank()) {
                    TrailingIconButton(
                        icon = Icons.Default.Clear,
                        contentDescription = "Anna etäohjauksen GSM-numero",
                        tint = Color.Black,
                        onClick = { viewModel.updateRemoteNumber("") }
                    )
                }
            },
            supportingText = {
                if (viewModel.remoteNumber.isEmpty()) {
                    Text(
                        text = "Syötä GSM-numero",
                        color = Color.Red
                    )
                }
            }
        )
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp, vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            value = viewModel.heatPumpNumber,
            onValueChange = { viewModel.updateHeatPumpNumber(it) },
            label = { Text("Ilmalämpöpumpun GSM-numero") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            trailingIcon = {
                if (viewModel.heatPumpNumber.isNotBlank()) {
                    TrailingIconButton(
                        icon = Icons.Default.Clear,
                        contentDescription = "Anna ilmalämpöpumpun GSM-numero",
                        tint = Color.Black,
                        onClick = { viewModel.updateHeatPumpNumber("") }
                    )
                }
            },
            supportingText = {
                if (viewModel.heatPumpNumber.isEmpty()) {
                    Text(
                        text = "Syötä GSM-numero",
                        color = Color.Red
                    )
                }
            }
        )
        Button(
            enabled = true,
            modifier = modifier.padding(10.dp),
            onClick = {
                coroutineScope.launch {
                    viewModel.saveUserName(context)
                    viewModel.saveRemoteNumber(context)
                    viewModel.saveHeatPumpNumber(context)
                }
            }
        ) {
            Text("Tallenna")
        }
    }
}