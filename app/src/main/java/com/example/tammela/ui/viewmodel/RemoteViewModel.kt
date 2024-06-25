package com.example.tammela.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tammela.data.model.CommandHistory
import com.example.tammela.data.model.CommandStatus

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RemoteViewModel : ViewModel() {
    private val _status = MutableStateFlow(CommandStatus("", "", "", "", "", ""))
    val status: StateFlow<CommandStatus> = _status

    private val _history = MutableStateFlow<Array<CommandHistory>?>(emptyArray())
    val history: StateFlow<Array<CommandHistory>?> = _history

    private val _user = MutableStateFlow("ALL")
    val user: StateFlow<String> = _user

    private val _device = MutableStateFlow("0")
    val device: StateFlow<String> = _device

    private val _amount = MutableStateFlow("1")
    val amount: StateFlow<String> = _amount

    fun getRemoteCommand() {
        val userValue = _user.value
        val amountValue = _amount.value
        val deviceValue = _device.value
        val url = "https://www.isoseppo.fi/eTammela/api/system/get_command_history.php?user=$userValue&system=Tammela&device=$deviceValue&amount=$amountValue"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            Fuel.get(url)
                .header(header)
                .responseObject(CommandStatus.Deserializer()) { _, _, result ->
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
                                _history.value = CommandHistory.Deserializer().deserialize(it.extra)
                            }
                        }
                    }
                }
        }
    }

    fun sendRemoteData( user: String, command: String) {
        val url = "https://www.isoseppo.fi/eTammela/api/system/save_control_command.php?user=Seppo&system=Tammela&command=$command&device=0"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            Fuel.get(url)
                .header(header)
                .responseObject(CommandStatus.Deserializer()) { _, _, result ->
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
                                _history.value = CommandHistory.Deserializer().deserialize(it.extra)
                            }
                        }
                    }
                }
        }
    }

    fun refresRemoteData() {
        viewModelScope.launch {
            getRemoteCommand()
        }
    }

    fun setUser(newUser: String) {
        _user.value = newUser
        refresRemoteData()
    }

    fun setDevice(newDevice: String) {
        _device.value = newDevice
        refresRemoteData()
    }

    fun setAmount(newAmount: String) {
        _amount.value = newAmount
        refresRemoteData()
    }
}