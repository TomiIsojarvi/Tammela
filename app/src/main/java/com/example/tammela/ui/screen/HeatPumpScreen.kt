package com.example.tammela.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.ui.component.CommandButton
import com.example.tammela.ui.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun HeatPumpScreen(
    context: Context,
    modifier: Modifier = Modifier,
) {
    val settingsViewModel: SettingsViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }
    var textCommand: String by remember { mutableStateOf("") }

    // Slider
    var sliderPosition by remember { mutableFloatStateOf(16f) }

    // Speed Radio buttons
    val radioSpeedOptions = listOf("Auto", "Low", "Medium", "High")
    val (selectedSpeedOption, onSpeedOptionSelected) = remember { mutableStateOf(radioSpeedOptions[0]) }

    // History Radio buttons
    val radioHistoryOptions = listOf("Kaikki", "Omat" )
    val (selectedHistoryOption, onHistoryOptionSelected) = remember { mutableStateOf(radioHistoryOptions[0]) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            settingsViewModel.loadUserData(context)
            isLoading = false
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            coroutineScope.launch {
                sendSms(context, settingsViewModel, textCommand)
            }
        } else {
            Toast.makeText(context, "SMS permission denied", Toast.LENGTH_LONG).show()
        }
    }

    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //-----------------------------------------------------------------
            Text(
                fontWeight = FontWeight(700),
                style = MaterialTheme.typography.titleSmall,
                text = "Aseta tavoitelämpötila:"
            )
            Spacer(modifier = Modifier.height(5.dp))
            //-----------------------------------------------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    steps = 13,
                    valueRange = 16f..30f,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${sliderPosition.toInt()} °C",
                    //modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            //-----------------------------------------------------------------
            Text(
                fontWeight = FontWeight(700),
                style = MaterialTheme.typography.titleSmall,
                text = "Aseta tuulettimen nopeus:"
            )
            Spacer(modifier = Modifier.height(5.dp))
            //-----------------------------------------------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                radioSpeedOptions.forEach { text ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = (text == selectedSpeedOption),
                            onClick = {
                                onSpeedOptionSelected(text)

                                // Set user based on the selected option
                                /*if (text == "Kaikki") {
                                    remoteViewModel.setUser("ALL")
                                    remoteViewModel.setAmount("ALL")
                                } else {
                                    remoteViewModel.setUser(settingsViewModel.username)
                                    remoteViewModel.setAmount("ALL")
                                }*/
                            }
                        )
                        Text(text = text)
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            //-----------------------------------------------------------------
            Text(
                fontWeight = FontWeight(700),
                style = MaterialTheme.typography.titleSmall,
                text = "Anna komento:"
            )
            Spacer(modifier = Modifier.height(5.dp))
            //-----------------------------------------------------------------
//-----------------------------------------------------------------
            Row(
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding( bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "LÄMMITÄ",
                    onClick = {
//                        textCommand = "TULOSSA"
//                        showDialog = true
                    },
                )
                Spacer(modifier = Modifier.width(5.dp))
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "KUIVAA",
                    onClick = {
//                        textCommand = "PAIKALLA"
//                        showDialog = true
                    },
                )

            }
            //-----------------------------------------------------------------
            Row(
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "JÄÄHDYTÄ",
                    onClick = {
//                        textCommand = "POISSA"
//                        showDialog = true
                    },
                )
                Spacer(modifier = Modifier.width(5.dp))
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "AUTO",
                    onClick = {
//                        textCommand = "TULOSSA"
//                        showDialog = true
                    },
                )
            }
            //-----------------------------------------------------------------
            Row(
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "LÄMPÖTILA",
                    onClick = {
//                        textCommand = "PAIKALLA"
//                        showDialog = true
                    },
                )
                Spacer(modifier = Modifier.width(5.dp))
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "SAMMUTA",
                    onClick = {
//                        textCommand = "POISSA"
//                        showDialog = true
                    },
                )
            }
            //-----------------------------------------------------------------
            Text(
                fontWeight = FontWeight(700),
                style = MaterialTheme.typography.titleSmall,
                text = "Aikaisemmat komennot:"
            )
            Spacer(modifier = Modifier.height(5.dp))
            //-----------------------------------------------------------------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                radioHistoryOptions.forEach { text ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = (text == selectedHistoryOption),
                            onClick = {
                                onHistoryOptionSelected(text)

                                // Set user based on the selected option
                                /*if (text == "Kaikki") {
                                    remoteViewModel.setUser("ALL")
                                    remoteViewModel.setAmount("ALL")
                                } else {
                                    remoteViewModel.setUser(settingsViewModel.username)
                                    remoteViewModel.setAmount("ALL")
                                }*/
                            }
                        )
                        Text(text = text)
                    }
                }
            }
        }
    }
}