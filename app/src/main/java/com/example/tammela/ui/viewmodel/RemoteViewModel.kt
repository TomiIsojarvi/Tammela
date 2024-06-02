package com.example.tammela.ui.viewmodel

import androidx.compose.runtime.MutableState
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

    private val _user = MutableStateFlow("ALL")
    val user: StateFlow<String> = _user

    private val _amount = MutableStateFlow("1")
    val amount: StateFlow<String> = _amount
    fun getRemote() {
        val userValue = _user.value
        val amountValue = _amount.value
        val url = "https://www.isoseppo.fi/eTammela/api/system/get_command_history.php?user=$userValue&system=Tammela&amount=$amountValue"
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

    fun setUser(newUser: String) {
        _user.value = newUser
        refresRemoteData()
    }

    fun setAmount(newAmount: String) {
        _amount.value = newAmount
        refresRemoteData()
    }
}