package com.example.tammela.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.CastConnected
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.ui.component.StartInfo
import com.example.tammela.ui.viewmodel.HeatPumpViewModel
import com.example.tammela.ui.viewmodel.RemoteViewModel
import com.example.tammela.ui.viewmodel.SensorViewModel
import com.example.tammela.ui.viewmodel.SettingsViewModel
import com.example.tammela.ui.viewmodel.UserAuthViewModel
import kotlinx.coroutines.launch


data class ButtonData(val icon: ImageVector, val text: String, val onClick: () -> Unit, var enabled: Boolean)

@Composable
fun ButtonItem(buttonData: ButtonData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .padding(8.dp),
            shape = CircleShape,
            color = Color.Gray.copy(alpha = 0.2f)
        ) {
            IconButton(
                enabled = buttonData.enabled,
                onClick = buttonData.onClick,
            ) {
                Icon(
                    imageVector = buttonData.icon,
                    contentDescription = buttonData.text
                )
            }
        }
        Text(buttonData.text)
    }
}

@Composable
fun ButtonRow(buttons: List<ButtonData>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        buttons.forEach{
                e -> ButtonItem(e)
        }
    }
}

@Composable
fun StartScreen(
    onMetersClicked: () -> Unit,
    onRemoteClicked: () -> Unit,
    onHeatPumpClicked: () -> Unit,
    onShoppingListClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier,
    context: Context
) {
    val sensorViewModel: SensorViewModel = viewModel()
    val remoteViewModel: RemoteViewModel = viewModel()
    val heatPumpViewModel: HeatPumpViewModel = viewModel()
    val userAuthViewModel: UserAuthViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = viewModel()

    var isLoading by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    val buttons = remember {
        listOf(
            ButtonData(Icons.Outlined.Speed, "Mittarit", onMetersClicked, true),
            ButtonData(Icons.Outlined.CastConnected, "Etäohjaus", onRemoteClicked, false),
            ButtonData(Icons.Outlined.Air, "ILP", onHeatPumpClicked, false),
            ButtonData(Icons.Outlined.NoteAlt, "Ostoslista", onShoppingListClicked, true),
            ButtonData(Icons.Outlined.Settings, "Asetukset", onSettingsClicked, true),
        )
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            settingsViewModel.loadUserData(context)
            isLoading = false
        }
    }

    val isValidUsername by userAuthViewModel.isValid.collectAsState()

    LaunchedEffect(settingsViewModel.username) {
        if (settingsViewModel.username.isNotEmpty()) {
            userAuthViewModel.fetchUserAuthData(settingsViewModel.username, context)
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
        if (isValidUsername && settingsViewModel.username.isNotEmpty()) {
            if (settingsViewModel.remoteNumber.isNotEmpty()) {
                buttons[1].enabled = true
            } else {
                buttons[1].enabled = false
            }

            if (settingsViewModel.heatPumpNumber.isNotEmpty()) {
                buttons[2].enabled = true
            } else {
                buttons[2].enabled = false
            }
        } else {
            buttons[1].enabled = false
            buttons[2].enabled = false
        }

        Column {
            StartInfo(sensorViewModel, remoteViewModel, heatPumpViewModel, modifier)
            ButtonRow(buttons)
        }
    }
}