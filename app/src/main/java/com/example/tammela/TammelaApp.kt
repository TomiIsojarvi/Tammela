package com.example.tammela

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tammela.ui.screen.SensorScreen
import com.example.tammela.ui.screen.SettingsScreen
import com.example.tammela.ui.screen.StartScreen

//-----------------------------------------------------------------------------
// enum values that represent the screens in the app
//-----------------------------------------------------------------------------
enum class AppScreen(val title: String) {
    Start("Tammela"),
    Remote("Etäohjaus"),
    HeatPump("Ilmalämpöpumppu"),
    Sensors("Mittarit"),
    ShoppingList("Ostoslista"),
    Settings("Asetukset")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TammelaAppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(currentScreen.title) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Takaisin"
                    )
                }
            }
        }
    )
}

@Composable
fun TammelaApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route?: AppScreen.Start.name
    )

    fun onChangeScreen(screen: AppScreen) {
        navController.navigate(screen.name)
    }

    Scaffold(
        topBar = {
            TammelaAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Start Screen
            composable(route = AppScreen.Start.name) {
                StartScreen(
                    onMetersClicked = {navController.navigate(AppScreen.Sensors.name)},
                    onRemoteClicked = {navController.navigate(AppScreen.Remote.name)},
                    onHeatPumpClicked = {navController.navigate(AppScreen.HeatPump.name)},
                    onShoppingListClicked = {navController.navigate(AppScreen.ShoppingList.name)},
                    onSettingsClicked = {navController.navigate(AppScreen.Settings.name)},
                )
            }

            // Sensors Screen
            composable(route = AppScreen.Sensors.name) {
                SensorScreen(modifier)
            }

            // Remote Control Screen
            composable(route = AppScreen.Remote.name) {
            }

            // Heat Pump Screen
            composable(route = AppScreen.HeatPump.name) {
                //HeatPumpScreen()
            }

            // Shopping List Screen
            composable(route = AppScreen.ShoppingList.name) {
                //ShoppingListScreen()
            }

            // Settings Screen
            composable(route = AppScreen.Settings.name) {
                val context = LocalContext.current
                SettingsScreen(context)
            }
        }
    }
}