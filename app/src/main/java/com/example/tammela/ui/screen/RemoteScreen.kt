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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.tammela.ui.viewmodel.RemoteViewModel
import com.example.tammela.ui.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.byUnicodePattern


@Composable
fun RemoteScreen(
    context: Context,
    modifier: Modifier = Modifier,
) {
    val remoteViewModel: RemoteViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var textCommand: String by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    val radioOptions = listOf("Kaikki", "Omat")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val remoteHistoryState by remoteViewModel.history.collectAsState(initial = emptyArray())

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            settingsViewModel.loadUserData(context)
            isLoading = false
        }
        remoteViewModel.setUser("ALL")
        remoteViewModel.setAmount("ALL")
        remoteViewModel.getRemoteCommand()
        //remoteViewModel.sendRemoteData("moi")
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
                text = "Komennot:"
            )
            Spacer(modifier = Modifier.height(10.dp))
            //-----------------------------------------------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "Tulossa",
                    onClick = {
                        textCommand = "TULOSSA"
                        showDialog = true
                        //remoteViewModel.sendRemoteData(textCommand)
                    },
                )
                Spacer(modifier = Modifier.width(5.dp))
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "Paikalla",
                    onClick = {
                        textCommand = "PAIKALLA"
                        showDialog = true
                    },
                )
                Spacer(modifier = Modifier.width(5.dp))
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "Poissa",
                    onClick = {
                        textCommand = "POISSA"
                        showDialog = true
                    },
                )
            }
            //-----------------------------------------------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CommandButton(
                    modifier = Modifier.weight(1f),
                    text = "Tilanne",
                    onClick = {
                        textCommand = "TILANNE"
                        showDialog = true
                    },
                )
                Spacer(modifier = Modifier
                    .width(5.dp)
                    .weight(2f))
            }
            //-----------------------------------------------------------------
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                fontWeight = FontWeight(700),
                style = MaterialTheme.typography.titleSmall,
                text = "Aikaisemmat komennot:"
            )
            Spacer(modifier = Modifier.height(10.dp))
            //-----------------------------------------------------------------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                radioOptions.forEach { text ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)

                                // Set user based on the selected option
                                if (text == "Kaikki") {
                                    remoteViewModel.setUser("ALL")
                                    remoteViewModel.setAmount("ALL")
                                } else {
                                    remoteViewModel.setUser(settingsViewModel.username)
                                    remoteViewModel.setAmount("ALL")
                                }
                            }
                        )
                        Text(text = text)
                    }
                }
            }
            //-----------------------------------------------------------------
            Spacer(modifier = Modifier.height(10.dp))
            val remoteHistory = remoteHistoryState
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
                            sendSms(context, settingsViewModel, textCommand)
                            remoteViewModel.sendRemoteData("Seppo", textCommand)
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

suspend fun sendSms(context: Context, settingsViewModel: SettingsViewModel, message: String) {
    settingsViewModel.loadUserData(context)
    val phoneNumber = settingsViewModel.remoteNumber

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
