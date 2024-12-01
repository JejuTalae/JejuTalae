package com.example.jejutalae.navigate


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jejutalae.screen.busstopscreen.BusStopScreen
import com.example.jejutalae.screen.homescreen.HomeScreen
import com.example.jejutalae.screen.SplashScreen
@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash"){
            SplashScreen(navController = navController)
        }
        composable("locationTracking") {
            HomeScreen(navController = navController)
        }
        composable(
            route = "busStop/{busId}/{busStationName}",
            arguments = listOf(
                navArgument("busId") { type = NavType.StringType },
                navArgument("busStationName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val busId = backStackEntry.arguments?.getString("busId")
            val busStationName = backStackEntry.arguments?.getString("busStationName")
            if (busId != null && busStationName != null) {
                BusStopScreen(busId = busId, busStationName = busStationName, navController = navController)
            }
        }
    }
}
