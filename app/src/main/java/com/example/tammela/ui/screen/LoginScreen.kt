package com.example.tammela.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tammela.TammelaApp
import com.example.tammela.ui.viewmodel.SettingsViewModel
import com.example.tammela.ui.viewmodel.UserAuthViewModel
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val settingsViewModel: SettingsViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    //  Remember the local value of username
    // var username by remember { mutableStateOf(settingsViewModel.username) }

    // Load initial data when the composable is first composed
    LaunchedEffect(Unit) {
        settingsViewModel.loadUserData(context)
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
            value = settingsViewModel.newUsername,
            onValueChange = { settingsViewModel.updateNewUsername(it) },
            label = { Text("Käyttäjätunnus") },
            singleLine = true,
            //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            trailingIcon = {
                if (settingsViewModel.newUsername.isNotBlank()) {
                    TrailingIconButton(
                        icon = Icons.Default.Clear,
                        contentDescription = "Tyhjennä",
                        tint = Color.Black,
                        onClick = { settingsViewModel.updateNewUsername("") }
                    )
                }
            },
            supportingText = {
                if (settingsViewModel.newUsername.isEmpty()) {
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
            value = settingsViewModel.remoteNumber,
            onValueChange = { settingsViewModel.updateRemoteNumber(it) },
            label = { Text("Etäohjauksen GSM-numero") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            trailingIcon = {
                if (settingsViewModel.remoteNumber.isNotBlank()) {
                    TrailingIconButton(
                        icon = Icons.Default.Clear,
                        contentDescription = "Tyhjennä",
                        tint = Color.Black,
                        onClick = { settingsViewModel.updateRemoteNumber("") }
                    )
                }
            },
        )
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp, vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            value = settingsViewModel.heatPumpNumber,
            onValueChange = { settingsViewModel.updateHeatPumpNumber(it) },
            label = { Text("Ilmalämpöpumpun GSM-numero") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            trailingIcon = {
                if (settingsViewModel.heatPumpNumber.isNotBlank()) {
                    TrailingIconButton(
                        icon = Icons.Default.Clear,
                        contentDescription = "Tyhjennä",
                        tint = Color.Black,
                        onClick = { settingsViewModel.updateHeatPumpNumber("") }
                    )
                }
            },
        )
        Button(
            enabled = settingsViewModel.newUsername.isNotEmpty(),
            modifier = modifier.padding(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray.copy(alpha = 0.2f),
                contentColor = Color.Black),
            onClick = {
                coroutineScope.launch {
                    settingsViewModel.saveNewUserName(context)
                    settingsViewModel.saveRemoteNumber(context)
                    settingsViewModel.saveHeatPumpNumber(context)
                }
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Login,
                contentDescription = "Save",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text("Kirjaudu")
        }
    }
}