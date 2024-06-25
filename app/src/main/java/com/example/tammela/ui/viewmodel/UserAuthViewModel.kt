package com.example.tammela.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class UserAuthViewModel : ViewModel() {

    // MutableStateFlow to hold the current user authentication state
    private val _userAuthState = MutableStateFlow<UserAuth?>(null)

    // Expose the user authentication state as StateFlow
    val userAuthState: StateFlow<UserAuth?> = _userAuthState

    // Function to update the user authentication state
    fun updateUserAuth(newUserAuth: UserAuth) {
        _userAuthState.value = newUserAuth
    }

    // Function to clear the user authentication state
    fun clearUserAuth() {
        _userAuthState.value = null
    }

    // Private MutableStateFlow to hold the validity of the user authentication
    private val _isValid = MutableStateFlow(false)

    // Expose the validity of the user authentication as StateFlow
    val isValid: StateFlow<Boolean> = _isValid

    // Function to fetch user authentication data from a remote source
    fun fetchUserAuthData(username: String, context: Context) {
        val url = "https://www.isoseppo.fi/eTammela/api/users/get_user_authorization.php?user=${username}&system=Tammela"
        val header = emptyMap<String, String>()

        Fuel.get(url)
            .header(header)
            .responseObject(UserAuth.Deserializer()) { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        _isValid.value = false
                    }
                    is Result.Success -> {
                        val (data, _) = result
                        data?.let {
                            _userAuthState.value = it
                            _isValid.value = it.reply == "OK"
                        }
                    }
                }
            }
    }
}
