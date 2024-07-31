package com.example.tammela.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tammela.data.model.Sensor

@Composable
fun SensorCard(sensor: Sensor, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.padding(10.dp),
        color = Color(0xFFE5E5E5)/*MaterialTheme.colorScheme.background*/,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        shadowElevation = 10.dp
    ) {
        Column (modifier = Modifier.fillMaxWidth())
        {
            Row (
                modifier = Modifier.padding(10.dp),
            )
            {
                Text(
                    modifier =  Modifier.weight(1f),
                    fontWeight = FontWeight(700),
                    text = sensor.name
                )
                Text(text=sensor.time)
            }
            ///////////////////////////////////////////////////////////////////
            Row{
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight(700),
                    text = String.format("%.2f", sensor.temperature),
                )
                Text(
                    style = MaterialTheme.typography.headlineSmall,
                    text = " °C"
                )
            }
            ///////////////////////////////////////////////////////////////////
            Column {
                Row (modifier = Modifier.padding(start = 10.dp))
                {
                    Text(
                        text = "Ilmankosteus: "
                    )
                    Text(
                        fontWeight = FontWeight(700),
                        text = sensor.humidity.toString()
                    )
                    Text(
                        text =" % "
                    )
                }
                ///////////////////////////////////////////////////////////////
                Row (modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp)) {
                    Text("Patterijännite ")
                    Text(
                        fontWeight = FontWeight(700),
                        text = sensor.battery.toString()
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = " V")
                    Text("RSSI ")
                    Text(
                        fontWeight = FontWeight(700),
                        text = sensor.rssi.toString()
                    )
                    Text(" dBm")
                }

            }
        }
    }
}
