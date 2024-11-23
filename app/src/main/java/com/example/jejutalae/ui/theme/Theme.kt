package com.example.jejutalae.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// 쓰지 말기(Color.kt 에서 정의된 색상 사용)
private val ColorScheme = lightColorScheme(
    primary = LightBlue,
    secondary = SoftBlue,
    tertiary = BrightRed,
)

@Composable
fun JejuTalaeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}