package com.example.tammela

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.tammela.ui.viewmodel.SettingsViewModel

val Context.dataStore by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by lazy {
        ViewModelProvider(this).get(SettingsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LaunchedEffect(Unit) {
                settingsViewModel.loadUserData(applicationContext)
                if (settingsViewModel.username.isEmpty()) {
                    settingsViewModel.updateUsername("Test")
                    settingsViewModel.saveUserName(applicationContext)
                }
            }
            TammelaApp()
        }
    }
}

