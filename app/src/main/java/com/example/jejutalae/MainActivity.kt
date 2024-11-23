package com.example.jejutalae

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.jejutalae.navigate.NavGraph
import com.example.jejutalae.ui.theme.JejuTalaeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JejuTalaeTheme {
                    NavGraph()
                }
            }
        }
    }