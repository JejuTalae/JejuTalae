package com.example.jejutalae

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jejutalae.ui.theme.Typography
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DialExample(
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFFFF))){}
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(0.65f)
                .background(Color(0xFFF5F5F5)), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("시간을 선택해주세요", style = Typography.titleMedium.copy(color = Color.Black), modifier = Modifier.offset(x = 16.dp))
            Spacer(modifier = Modifier.height(15.dp))
            TimePicker(
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    clockDialColor = Color(0xFFFFFFFF),
                    selectorColor = Color(0xff41C3E7),
                    timeSelectorSelectedContainerColor = Color(0xff41C3E7),
                    timeSelectorUnselectedContainerColor = Color(0xFFFFFFFF),
                    periodSelectorSelectedContainerColor = Color(0xff41C3E7),
                    periodSelectorUnselectedContainerColor = Color(0xFFFFFFFF),
                )
            )
            Row(
                modifier = Modifier.width(250.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
                    border = BorderStroke(width = 1.dp, color = Color(0xff41C3E7))
                ) {
                    Text("취소하기", style = Typography.labelSmall.copy(color = Color.Black))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff41C3E7))
                ) {
                    Text("시간 선택", style = Typography.labelSmall)
                }
            }
        }
    }
}