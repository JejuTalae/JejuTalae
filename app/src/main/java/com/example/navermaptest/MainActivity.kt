package com.example.navermaptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.navermaptest.ui.theme.NaverMapTestTheme
import com.naver.maps.map.compose.ExperimentalNaverMapApi

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalNaverMapApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NaverMapTestTheme {
                //LocationTrackingScreen()
                BusStopScreen()
                }
            }
        }
    }