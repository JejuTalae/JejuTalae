package com.example.jejutalae.screen.homescreen

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
import androidx.compose.material3.AlertDialog
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
import com.example.jejutalae.ui.theme.LightBlue
import com.example.jejutalae.ui.theme.PureWhite
import com.example.jejutalae.ui.theme.Typography
import java.time.LocalTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialExample(
    onConfirm: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    // 다이얼로그로 표시
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "시간을 선택해주세요",
                    style = Typography.titleMedium.copy(color = Color.Black),
                )
                Spacer(modifier = Modifier.height(15.dp))
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        clockDialColor = PureWhite,
                        selectorColor = LightBlue,
                        timeSelectorSelectedContainerColor = LightBlue,
                        timeSelectorUnselectedContainerColor = PureWhite,
                        periodSelectorSelectedContainerColor = LightBlue,
                        periodSelectorUnselectedContainerColor = PureWhite,
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // 선택된 시간을 콜백으로 전달
                    val selectedTime = LocalTime.of(
                        timePickerState.hour,
                        timePickerState.minute
                    )
                    onConfirm(selectedTime)
                },
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                border = BorderStroke(width = 1.dp, color = PureWhite)
            ) {
                Text("시간 선택", style = Typography.labelSmall.copy(color = Color.White))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = PureWhite),
                border = BorderStroke(width = 1.dp, color = LightBlue)
            ) {
                Text("취소하기", style = Typography.labelSmall.copy(color = LightBlue))
            }
        }
    )
}