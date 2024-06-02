package com.example.tammela

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tammela.ui.component.SensorCardList
import com.example.tammela.ui.screen.SensorScreen
import com.example.tammela.ui.screen.StartScreen
import com.example.tammela.ui.theme.TammelaTheme
import com.example.tammela.ui.viewmodel.SensorViewModel
import android.Manifest;

class MainActivity : ComponentActivity() {

    private val viewModel: SensorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TammelaApp()
        }
    }
}
