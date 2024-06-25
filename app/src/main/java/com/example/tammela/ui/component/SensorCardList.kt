package com.example.tammela.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tammela.ui.viewmodel.SensorViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SensorCardList(
    viewModel: SensorViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val sensors by viewModel.sensors.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSensors()
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(sensors) { sensor ->
                SensorCard(sensor = sensor)
            }
        }
    }
}

