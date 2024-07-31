package com.example.tammela.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tammela.data.model.Sensor
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SensorViewModel : ViewModel() {
    private val _sensors = MutableStateFlow<List<Sensor>>(emptyList())
    val sensors: StateFlow<List<Sensor>> = _sensors

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getSensors() {
        val url = "https://www.isoseppo.fi/eTammela/api/get_ruuviTag_info.php?system=Tammela"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            Fuel.get(url)
                .header(header)
                .responseObject(Sensor.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
                        }
                        is Result.Success -> {
                            val (data, _) = result
                            data?.let { _sensors.value = ArrayList(it.toList()) }
                        }
                    }
                    _isLoading.value = false
                }
        }
    }

    fun refreshSensorData() {
        viewModelScope.launch {
            getSensors()
        }
    }

    fun clearSensorData() {
        _sensors.value = emptyList()
    }
}

