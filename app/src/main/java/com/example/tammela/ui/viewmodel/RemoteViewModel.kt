package com.example.tammela.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tammela.data.model.RemoteHistory
import com.example.tammela.data.model.RemoteStatus
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RemoteViewModel : ViewModel() {
    private val _status = MutableStateFlow(RemoteStatus("", "", "", "", ""))
    val status: StateFlow<RemoteStatus> = _status

    private val _history = MutableStateFlow<Array<RemoteHistory>?>(emptyArray())
    val history: StateFlow<Array<RemoteHistory>?> = _history

    fun getRemote() {
        val url = "https://www.isoseppo.fi/eTammela/api/system/get_command_history.php?user=ALL&system=Tammela&amount=5"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            Fuel.get(url)
                .header(header)
                .responseObject(RemoteStatus.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            // Handle the error appropriately
                            println("Error fetching status: ${ex.message}")
                        }
                        is Result.Success -> {
                            val (data, _) = result
                            data?.let {
                                _status.value = it
                                _history.value = RemoteHistory.Deserializer().deserialize(it.extra)
                            }
                        }
                    }
                }
        }
    }

    fun refresRemoteData() {
        viewModelScope.launch {
            getRemote()
        }
    }
}