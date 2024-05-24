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

class SensorViewModel: ViewModel() {
    /*val _sensors: MutableState<ArrayList<Sensor>> = mutableStateOf(ArrayList())
    val sensors: MutableState<ArrayList<Sensor>> = _sensors*/

    /*val _error: MutableState<String?> = mutableStateOf(null)
    val error: State<String?> = _error*/

    private val _sensors = MutableStateFlow<List<Sensor>>(emptyList())
    val sensors: StateFlow<List<Sensor>> = _sensors

    fun getSensors() {
        val url = "https://www.isoseppo.fi/eTammela/api/get_ruuviTag_info.php?system=Tammela"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            Fuel.get(url)
                .header(header)
                .responseObject(Sensor.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            //_error.value = ex.response.statusCode.toString()
                        }
                        is Result.Success -> {
                            val (data, _) = result
                            data?.let { _sensors.value = ArrayList(it.toList()) }
                        }
                    }
                }
        }
    }

    fun refreshSensorData() {
        viewModelScope.launch {
            // Fetch or update your sensor data here
            getSensors()
        }
    }
}
