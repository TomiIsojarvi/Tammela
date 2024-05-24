package com.example.tammela

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.tammela.ui.component.SensorCardList
import com.example.tammela.ui.screen.SensorScreen
import com.example.tammela.ui.screen.StartScreen
import com.example.tammela.ui.theme.TammelaTheme
import com.example.tammela.ui.viewmodel.SensorViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SensorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TammelaApp()
        }
    }
}
