package com.example.tammela.ui.component

import android.health.connect.datatypes.units.Temperature
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tammela.data.model.Sensor

@Composable
fun TemperatureTile(/*sensor: Sensor*/title: String, modifier: Modifier = Modifier) {
    Box {
        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(
                fontWeight = FontWeight(700),
                style = MaterialTheme.typography.titleSmall,
                text = title
            )
            Row {
                Text(
                    //modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight(700),
                    text = /*sensor.temperature.toString()*/ "45.56"
                )
                Text(
                    style = MaterialTheme.typography.headlineSmall,
                    text = " °C"
                )
            }
        }
    }
}

/*
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TemperatureTilePreview() {
    TemperatureTile(
        sensor = Sensor(
            "Sisällä",
            "21.05.2024 08:58:50",
            45.56,
            4.56,
            3.34,
            "",
            -34
        )
    )
}*/
