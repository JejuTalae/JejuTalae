package com.example.jejutalae

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Icon(
                painter = painterResource(id = R.drawable.jejutalae),
                contentDescription = "My Icon",
                modifier = Modifier.size(180.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(20.dp))
            Icon(
                painter = painterResource(id = R.drawable.jejulogo),
                contentDescription = "My Icon",
                modifier = Modifier.size(260.dp),
                tint = Color.Unspecified
            )
        }
    }

    // 3초 후 LocationTrackingScreen으로 이동
    LaunchedEffect(Unit) {
        delay(3000L) // 3초 대기
        navController.navigate("locationTracking") {
            popUpTo("splash") { inclusive = true } // 스플래시 화면을 네비게이션 스택에서 제거
        }
    }
}