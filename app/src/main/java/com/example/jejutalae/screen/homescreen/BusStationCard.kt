package com.example.jejutalae.screen.homescreen

import androidx.compose.foundation.border
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
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun BusStationCard(modifier: Modifier = Modifier,
                   bus: Bus,
                   selectedTime: LocalTime?
) {
    val nextArrivalTime = selectedTime?.let { time ->
        bus.schedule.firstOrNull { it.isAfter(time) }
    }
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
                    if (selectedTime == null) {
                        Text(
                            text = "시간을 선택해주세요",
                            style = TextStyle(
                                fontFamily = nanumbarungothic,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                lineHeight = 24.sp
                            ),
                            color = MediumGray
                        )
                    } else if (nextArrivalTime != null) {
                        val duration = Duration.between(selectedTime, nextArrivalTime)
                        val hours = duration.toHours()
                        val minutes = duration.toMinutes() % 60

                        Text(
                            text = "${selectedTime.format(DateTimeFormatter.ofPattern("a h:mm"))}로부터 ",
                            style = TextStyle(
                                fontFamily = nanumbarungothic,
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp,
                                lineHeight = 28.sp
                            ),
                            color = MediumGray
                        )

                        val timeDifferenceText = buildString {
                            if (hours > 0) {
                                append("${hours}시간 ")
                            }
                            append("${minutes}분 후 도착")
                        }

                        Text(
                            text = timeDifferenceText,
                            style = TextStyle(
                                fontFamily = nanumbarungothic,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                lineHeight = 24.sp
                            ),
                            color = Color.Red
                        )
                    } else {
                        Text(
                            text = "오늘 내 도착 예정 정보 없음",
                            style = TextStyle(
                                fontFamily = nanumbarungothic,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                lineHeight = 24.sp
                            ),
                            color = MediumGray
                        )
                    }
                }
            }
        }
    }