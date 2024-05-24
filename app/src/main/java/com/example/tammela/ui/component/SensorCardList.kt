package com.example.tammela.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.tammela.ui.viewmodel.SensorViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun SensorCardList(
    viewModel: SensorViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    //val sensors by remember(viewModel) { viewModel.sensors }
    val sensors by remember { viewModel.sensors }.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = Unit) {
        viewModel.getSensors()
    }

    LazyColumn() {
        items(sensors) { sensor ->
            SensorCard(sensor = sensor)
        }
    }
}