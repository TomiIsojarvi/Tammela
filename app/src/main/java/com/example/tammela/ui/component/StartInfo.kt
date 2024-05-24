package com.example.tammela.ui.component

import android.health.connect.datatypes.units.Temperature
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tammela.data.model.RemoteHistory
import com.example.tammela.data.model.RemoteStatus
import com.example.tammela.ui.screen.ButtonRow
import com.example.tammela.ui.viewmodel.RemoteViewModel
import com.example.tammela.ui.viewmodel.SensorViewModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char

@Composable
fun TempTile(title: String, value: Double, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            fontWeight = FontWeight(700),
            style = MaterialTheme.typography.titleSmall,
            text = title
        )
        Row {
            Text(
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight(700),
                text = String.format("%.2f", value),
            )
            Text(
                style = MaterialTheme.typography.headlineSmall,
                text = " °C"
            )
        }
    }
}

@Composable
fun StartInfo(
    sensorViewModel: SensorViewModel = viewModel(),
    remoteViewModel: RemoteViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val sensors by sensorViewModel.sensors.collectAsState(initial = emptyList())
    val remoteStatus by remoteViewModel.status.collectAsState(initial = RemoteStatus("", "", "", "", ""))
    val remoteHistoryState by remoteViewModel.history.collectAsState(initial = emptyArray())

    LaunchedEffect(Unit) {
        sensorViewModel.getSensors()
        remoteViewModel.getRemote()
    }

    val indoorSensor = sensors.firstOrNull { it.name == "Olohuone" }
    val outdoorSensor = sensors.firstOrNull { it.name == "Ulkona" }

    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            if (indoorSensor != null && outdoorSensor != null) {
                /*indoorSensor.let {
                    TempTile(title = "Sisällä", value = it.temperature)
                }
                outdoorSensor.let {
                    TempTile(title = "Ulkona", value = it.temperature)
                }*/
                TempTile(title = "Sisällä", value = indoorSensor.temperature)
                TempTile(title = "Ulkona", value = outdoorSensor.temperature)
            } else {
                CircularProgressIndicator()
            }
        }
        Column (modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                fontWeight = FontWeight(700),
                style = MaterialTheme.typography.titleSmall,
                text = "Viimeisimmät tapahtumat:"
            )
            // Display RemoteHistory information safely
            val remoteHistory = remoteHistoryState
            Row (modifier.padding(16.dp))
            {
                if (remoteHistory != null && remoteHistory.isNotEmpty()) {
                    LazyColumn() {
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
                    /*Text(
                        text = "No history available",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )*/
                }
            }

        }


    }
}