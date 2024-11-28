package com.example.jejutalae.screen.homescreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jejutalae.data.Bus
import com.example.jejutalae.ui.theme.LightBlue
import com.example.jejutalae.ui.theme.MediumGray
import com.example.jejutalae.ui.theme.nanumbarungothic

@Composable
fun BusStationCard(modifier: Modifier = Modifier, bus: Bus) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 25.dp),
        ) {
        Box(
            modifier = Modifier
                .size(55.dp)
                .border(
                    width = 5.dp,
                    color = LightBlue,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = bus.busNo.toString(),
                color = LightBlue,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(
                text = "출발역 <-> 종착역",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Text(
                    text = "오전 7:00로 부터 ",
                    style = TextStyle(
                        fontFamily = nanumbarungothic,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        lineHeight = 28.sp),
                    color = MediumGray
                )
                Text(
                    text = "10분 후 도착",
                    style = TextStyle(
                        fontFamily = nanumbarungothic,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        lineHeight = 24.sp),
                    color = Color.Red
                )
            }
        }
    }
}