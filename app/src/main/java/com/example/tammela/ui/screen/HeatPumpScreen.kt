package com.example.tammela.ui.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.ui.component.CommandButton
import com.example.tammela.ui.component.ConfirmDialog
import com.example.tammela.ui.viewmodel.HeatPumpViewModel
import com.example.tammela.ui.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.byUnicodePattern

@Composable
fun HeatPumpScreen(
    context: Context,
    modifier: Modifier = Modifier,
) {
    val heatPumpViewModel: HeatPumpViewModel = viewModel()

    val radioOptions = listOf("Kaikki", "Omat")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val heatPumpHistoryState by heatPumpViewModel.history.collectAsState(initial = emptyArray())

    val settingsViewModel: SettingsViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }
    var textCommand: String by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    // Slider
    var sliderPosition by remember { mutableFloatStateOf(16f) }

    // Speed Radio buttons
    val radioSpeedOptions = listOf("Auto", "Low", "Medium", "High")
    var selectedSpeedCommand: String by remember { mutableStateOf("") }
    val (selectedSpeedOption, onSpeedOptionSelected) = remember { mutableStateOf(radioSpeedOptions[0]) }

    // History Radio buttons
    val radioHistoryOptions = listOf("Kaikki", "Omat" )
    val (selectedHistoryOption, onHistoryOptionSelected) = remember { mutableStateOf(radioHistoryOptions[0]) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            settingsViewModel.loadUserData(context)
            isLoading = false
        }
        heatPumpViewModel.setUser("ALL")
        heatPumpViewModel.setAmount("ALL")
        heatPumpViewModel.getHeatPumpCommand()
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            coroutineScope.launch {
                sendHeatPumpSms(context, settingsViewModel, textCommand)
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
                                when (text) {
                                    "Auto" -> selectedSpeedCommand = ""
                                    "Low" -> selectedSpeedCommand = " MIN"
                                    "Medium" -> selectedSpeedCommand = " MED"
                                    "High" -> selectedSpeedCommand = " MAX"
                                }
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
                        textCommand = "HEAT ${sliderPosition.toInt()}$selectedSpeedCommand"
                        showDialog = true
                    },
                )
                Spacer(modifier = Modifier.width(5.dp))
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "KUIVAA",
                    onClick = {
                        textCommand = "DRY ${sliderPosition.toInt()}$selectedSpeedCommand"
                        showDialog = true
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
                        textCommand = "COOL ${sliderPosition.toInt()}$selectedSpeedCommand"
                        showDialog = true
                    },
                )
                Spacer(modifier = Modifier.width(5.dp))
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "AUTO",
                    onClick = {
                        textCommand = "AUTO ${sliderPosition.toInt()}$selectedSpeedCommand"
                        showDialog = true
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
                        textCommand = "TEMP"
                        showDialog = true
                    },
                )
                Spacer(modifier = Modifier.width(5.dp))
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "SAMMUTA",
                    onClick = {
//                        textCommand = ""
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
                                if (text == "Kaikki") {
                                    heatPumpViewModel.setUser("ALL")
                                    heatPumpViewModel.setAmount("ALL")
                                } else {
                                    heatPumpViewModel.setUser(settingsViewModel.username)
                                    heatPumpViewModel.setAmount("ALL")
                                }
                            }
                        )
                        Text(text = text)
                    }
                }
            }
            //-----------------------------------------------------------------
            Spacer(modifier = Modifier.height(10.dp))
            val remoteHistory = heatPumpHistoryState
            if (remoteHistory != null && remoteHistory.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()

                ) {
                    items(remoteHistory) { item ->
                        var localDateTime = LocalDateTime.parse(
                            input = item.time,
                            format = LocalDateTime.Format {
                                byUnicodePattern("yyyy-MM-dd HH:mm:ss")
                            }
                        )

                        Row {
                            Text(
                                text = "${item.user}: ",
                                fontWeight = FontWeight(700),
                                style = MaterialTheme.typography.titleSmall,
                            )
                            Text(
                                text = "${localDateTime.format(
                                    LocalDateTime.Format {
                                        byUnicodePattern("d.M.yyyy HH.mm") }
                                )}, ")
                            Text(
                                text = "${item.state}",
                                fontWeight = FontWeight(700),
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                    }
                }

            } else {
                CircularProgressIndicator()
            }
        }

        if (showDialog) {
            ConfirmDialog(
                onDismissRequest = {
                    showDialog = false
                    Toast.makeText(context, "SMS-viestiä ei lähetetty", Toast.LENGTH_SHORT).show()
                },
                onConfirmation = {
                    showDialog = false
                    coroutineScope.launch {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
                            == PackageManager.PERMISSION_GRANTED
                        ) {
                            sendHeatPumpSms(context, settingsViewModel, textCommand)
                        } else {
                            requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
                        }
                    }
                },
                command = textCommand
            )
        }
    }
}

suspend fun sendHeatPumpSms(context: Context, settingsViewModel: SettingsViewModel, message: String) {
    settingsViewModel.loadUserData(context)
    val phoneNumber = settingsViewModel.heatPumpNumber

    if (phoneNumber.isNotBlank()) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(
                context,
                "Viesti lähetetty",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Virhe : " + e.message,
                Toast.LENGTH_LONG
            ).show()
        }
    } else {
        Toast.makeText(
            context,
            "Puhelinnumeroa ei ole määritelty",
            Toast.LENGTH_LONG
        ).show()
    }
}