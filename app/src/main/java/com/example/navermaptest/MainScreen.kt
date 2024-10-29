package com.example.navermaptest


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash"){
            SplashScreen(navController = navController)
        }
        composable("locationTracking") {
            LocationTrackingScreen(navController)
        }
        composable("busStop") {
            BusStopScreen(navController = navController)
        }
    }
}