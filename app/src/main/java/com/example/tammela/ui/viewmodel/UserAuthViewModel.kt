package com.example.tammela.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tammela.data.model.RemoteStatus
import com.example.tammela.data.model.Sensor
import com.example.tammela.data.model.UserAuth
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class UserAuthViewModel : ViewModel() {
    private val _authInfo = MutableStateFlow(UserAuth("", "", "", ""))
    val authInfo: StateFlow<UserAuth> = _authInfo

    private suspend fun getAuthInfo(userName: String): Boolean {
        val url = "https://www.isoseppo.fi/eTammela/api/users/get_user_authorization.php?user=${userName}&system=Tammela"
        val header = emptyMap<String, String>()

        val validationFlow = MutableStateFlow(false)

        Fuel.get(url)
            .header(header)
            .responseObject(UserAuth.Deserializer()) { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        validationFlow.value = false
                    }
                    is Result.Success -> {
                        val (data, _) = result
                        data?.let {
                            _authInfo.value = it
                            validationFlow.value = it.reply == "OK"
                        }
                    }
                }
            }

        return validationFlow.first()
    }

    val userNameHasError: StateFlow<Boolean> = snapshotFlow { "Seppo" }
        .mapLatest { userName ->
            getAuthInfo(userName)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )
}