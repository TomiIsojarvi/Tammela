package com.example.tammela.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tammela.ui.screen.dataStore
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

// Define the DataStore keys
val USER_NAME = stringPreferencesKey("user_name")
val NEW_USER_NAME = stringPreferencesKey("new_user_name")
val REMOTE_NUMBER = stringPreferencesKey("remote_number")
val HEAT_PUMP_NUMBER = stringPreferencesKey("heat_pump_number")

class SettingsViewModel : ViewModel() {
    var username by mutableStateOf("")
        private set

    var newUsername by mutableStateOf("")
        private set

    var remoteNumber by mutableStateOf("")
        private set

    var heatPumpNumber by mutableStateOf("")
        private set

    fun updateUsername(input: String) {
        username = input
    }

    fun updateNewUsername(input: String) {
        newUsername = input
    }

    fun updateRemoteNumber(input: String) {
        remoteNumber = input
    }

    fun updateHeatPumpNumber(input: String) {
        heatPumpNumber = input
    }

    fun saveUserName(context: Context) {
        viewModelScope.launch {
            context.dataStore.edit { settings ->
                settings[USER_NAME] = username
            }
        }
    }

    fun saveNewUserName(context: Context) {
        viewModelScope.launch {
            context.dataStore.edit { settings ->
                settings[USER_NAME] = newUsername
            }
        }
    }

    fun saveRemoteNumber(context: Context) {
        viewModelScope.launch {
            context.dataStore.edit { settings ->
                settings[REMOTE_NUMBER] = remoteNumber
            }
        }
    }

    fun saveHeatPumpNumber(context: Context) {
        viewModelScope.launch {
            context.dataStore.edit { settings ->
                settings[HEAT_PUMP_NUMBER] = heatPumpNumber
            }
        }
    }

    fun loadUserData(context: Context) {
        viewModelScope.launch {
            val preferences = context.dataStore.data.first()
            username = preferences[USER_NAME] ?: ""
            remoteNumber = preferences[REMOTE_NUMBER] ?: ""
            heatPumpNumber= preferences[HEAT_PUMP_NUMBER] ?: ""
        }
    }
}