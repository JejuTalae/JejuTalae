package com.example.jejutalae.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jejutalae.screen.busstopscreen.BusStopScreen
import com.example.jejutalae.screen.homescreen.HomeScreen
import com.example.jejutalae.screen.SplashScreen
import com.example.jejutalae.screen.busschedule.BusScheduleScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash"){
            SplashScreen(navController = navController)
        }
        composable("locationTracking") {
            HomeScreen(navController)
        }
        composable("busStop") {
            BusStopScreen(navController = navController)
        }
        composable("busSchedule") {
            BusScheduleScreen(navController = navController)
        }
    }
}