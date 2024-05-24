package com.example.tammela.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.CastConnected
import androidx.compose.material.icons.outlined.DeviceThermostat
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.ui.component.StartInfo
import com.example.tammela.ui.component.TemperatureTile
import com.example.tammela.ui.viewmodel.RemoteViewModel
import com.example.tammela.ui.viewmodel.SensorViewModel


data class ButtonData(val icon: ImageVector, val text: String, val onClick: () -> Unit)

@Composable
fun ButtonItem(buttonData: ButtonData) {
    Column(
        //modifier = Modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .padding(8.dp),
            shape = CircleShape,
            color = Color.Gray.copy(alpha = 0.2f)
        ) {
            // Muista FilledIconButton!!!
            IconButton(
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

//-----------------------------------------------------------------------------
// Start Screen for Tammela application
//-----------------------------------------------------------------------------
@Composable
fun StartScreen(
    onMetersClicked: () -> Unit,
    onRemoteClicked: () -> Unit,
    onHeatPumpClicked: () -> Unit,
    onShoppingListClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sensorViewModel: SensorViewModel = viewModel()
    val remoteViewModel: RemoteViewModel = viewModel()

    val buttons = remember {
        listOf(
            ButtonData(Icons.Outlined.Speed, "Anturit", onMetersClicked),
            ButtonData(Icons.Outlined.CastConnected, "Etäohjaus", onRemoteClicked),
            ButtonData(Icons.Outlined.Air, "ILP", onHeatPumpClicked),
            ButtonData(Icons.Outlined.NoteAlt, "Ostoslista", onShoppingListClicked),
            ButtonData(Icons.Outlined.Settings, "Asetukset", onSettingsClicked),
        )
    }
    Column {
        StartInfo(sensorViewModel, remoteViewModel,  modifier)
        ButtonRow(buttons)
    }
}

//-----------------------------------------------------------------------------
// Preview for Start Screen
//-----------------------------------------------------------------------------
@Preview(showBackground = true, device = "id:pixel_7_pro", showSystemUi = true)
@Composable
fun StartScreenPreview() {
    val onMetersClicked = {}
    val onRemoteClicked = {}
    val onAirPumpClicked = {}
    val onShoppingListClicked = {}
    val onSettingsClicked = {}

    StartScreen(
        onMetersClicked,
        onRemoteClicked,
        onAirPumpClicked,
        onShoppingListClicked,
        onSettingsClicked
    )
}