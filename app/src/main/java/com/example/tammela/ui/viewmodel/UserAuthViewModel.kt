package com.example.tammela.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.tammela.data.model.UserAuth
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserAuthViewModel : ViewModel() {

    // MutableStateFlow to hold the current user authentication state
    private val _userAuthState = MutableStateFlow<UserAuth?>(null)

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
