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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tammela.data.model.Sensor

@Composable
fun SensorCard(sensor: Sensor, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.padding(10.dp),
        color = MaterialTheme.colorScheme.surface,
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
                    /*Text("Ilmanpaine: ")
                    Text(
                        text = "0"
                    )
                    Text(" hPa")*/
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SensorCardPreview() {
    SensorCard(Sensor(
        "Olohuone",
        "21.05.2024 08:58:50",
        26.20,
        54.0,
        34.0,
        "jkdgkdgjkd",
        34),
        Modifier.padding(0.dp))
}
