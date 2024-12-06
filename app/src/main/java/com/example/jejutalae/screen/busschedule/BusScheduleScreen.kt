package com.example.jejutalae.screen.busschedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jejutalae.R
import com.example.jejutalae.data.bus_221_DGY
import com.example.jejutalae.data.bus_221_JBT
import com.example.jejutalae.data.bus_301_DGY
import com.example.jejutalae.data.bus_301_JBT
import com.example.jejutalae.data.bus_424_JBT
import com.example.jejutalae.data.bus_424_JCH
import com.example.jejutalae.ui.theme.BrightRed
import com.example.jejutalae.ui.theme.LightBlue
import com.example.jejutalae.ui.theme.MediumGray
import com.example.jejutalae.ui.theme.PureWhite
import com.example.jejutalae.ui.theme.Typography
import com.example.jejutalae.ui.theme.VeryLightGray
import com.example.jejutalae.ui.theme.nanumbarungothic
import java.time.Duration
import java.time.LocalTime

@Composable
fun BusScheduleScreen(
    startStation: String,
    endStation: String,
    selectedTime: LocalTime?
) {
    val convertedStartStation = when (startStation) {
        "제주시 버스터미널" -> "제주버스터미널[남]"
        else -> startStation
    } 
    val convertedEndStation = when (endStation) {
        "제주시청" -> "제주시청(아라방면)"
        "동광양" -> "동광양[남]"
        else -> endStation
    }

    val baseTime = selectedTime ?: LocalTime.now()
    // 실제 버스 데이터에서 경로 찾기
    val busRoutes = listOf(
        bus_301_JBT to bus_301_DGY,  
        bus_424_JBT to bus_424_JCH,
        bus_221_JBT to bus_221_DGY
    ).filter { (startBus, endBus) ->
        startBus.busStop.any { it.name == convertedStartStation } && 
        endBus.busStop.any { it.name == convertedEndStation }
    }.map { (startBus, endBus) ->
        // 다음 출발 시간 찾기
        val nextDeparture = startBus.schedule.firstOrNull { it.isAfter(baseTime) }
            ?: startBus.schedule.first()
        
        // 대기 시간 계산
        val duration = Duration.between(baseTime, nextDeparture)
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        
        // 대기 시간 텍스트 생성
        val waitingTimeText = buildString {
            if (hours > 0) {
                append("${hours}시간 ")
            }
            append("${minutes}분")
        }

        BusRouteInfo(
            busNumber = startBus.busNo.toString(),
            startStation = startStation,
            endStation = endStation,
            walkTimeStart = 2,
            busTime = 6,
            walkTimeEnd = if (startBus.busNo == 424) 3 else 6,
            departureTime = BusRouteInfo.formatTime(nextDeparture),
            arrivalAfterMinutes = waitingTimeText,
            waitingTime = duration,
            baseTime = baseTime
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        if (busRoutes.isEmpty()) {
            // 경로가 없을 때 표시할 UI
            Text(
                text = "해당 경로의 버스가 없습니다",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = Typography.labelSmall
            )
        } else {
            LazyColumn {
                items(busRoutes) { routeInfo ->
                    BusScheduleCard(routeInfo = routeInfo)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun BusScheduleCard(routeInfo: BusRouteInfo) {
    var isRealTime by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 상단 시간 정보
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = routeInfo.totalTime,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = routeInfo.timeRange,
                style = MaterialTheme.typography.bodyLarge,
                color = MediumGray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 버스 경로 표시 - 상단 부분
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 상단 아이콘들과 버스 번호
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val (startWeight, busWeight, endWeight) = calculateWeights(routeInfo)
                
                Box(
                    modifier = Modifier.weight(startWeight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.walking),
                        contentDescription = "도보",
                        tint = MediumGray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Box(
                    modifier = Modifier.weight(busWeight),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bus_station),
                            contentDescription = "버스",
                            tint = LightBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = routeInfo.busNumber,
                            color = LightBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Box(
                    modifier = Modifier.weight(endWeight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.walking),
                        contentDescription = "도보",
                        tint = MediumGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // 하단 시간 표시
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                val (startWeight, busWeight, endWeight) = calculateWeights(routeInfo)
                
                // 전체 배경 (회색 둥근 바)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = VeryLightGray,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(startWeight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${routeInfo.walkTimeStart}분",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MediumGray
                            )
                        }
                        
                        // 중앙 버스 시간 (파란색 바)
                        Box(
                            modifier = Modifier
                                .weight(busWeight)
                                .height(30.dp)
                                .background(
                                    color = LightBlue,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${routeInfo.busTime}분",
                                color = Color.White
                            )
                        }
                        
                        Box(
                            modifier = Modifier.weight(endWeight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${routeInfo.walkTimeEnd}분",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MediumGray
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 버스 상세 정보
        Row(
            modifier = Modifier.padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 버스 번호와 시간 정보
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 버스 번호
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
                        text = routeInfo.busNumber,
                        color = LightBlue,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // 시간
                Text(
                    text = "${routeInfo.busTime}분",
                    color = LightBlue,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 타임라인과 정류장 정보
            Box(modifier = Modifier.height(IntrinsicSize.Min)) {
                Row {
                    // 타임라인
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxHeight()
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        // 상단 점
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(LightBlue, CircleShape)
                        )
                        // 연결선
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .weight(1f)
                                .background(LightBlue)
                        )
                        // 하단 점
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(LightBlue, CircleShape)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // 정류장 정보
                    Column(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = routeInfo.startStation,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Column(
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = routeInfo.departureTime,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Row {
                                Text(
                                    text = "${BusRouteInfo.formatTime(routeInfo.baseTime)}로 부터 ",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MediumGray
                                )
                                if(isRealTime == false)
                                Text(
                                    text = routeInfo.arrivalAfterMinutes + " 후 도착",
                                    style = TextStyle(
                                        fontFamily = nanumbarungothic,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        lineHeight = 28.sp,
                                        color = BrightRed
                                    ),
                                )
                                else{
                                    Text(
                                        text = "도착예정 정보없음",
                                        style = TextStyle(
                                            fontFamily = nanumbarungothic,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            lineHeight = 28.sp,
                                            color = MediumGray
                                        ),
                                    )
                                }
                            }
                        }

                        Text(
                            text = routeInfo.endStation,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(height = 23.dp, width = 64.dp).absoluteOffset(x = 330.dp, y = (-115).dp))
    {
        Button(
            modifier = Modifier.size(height = 23.dp, width = 64.dp),
            onClick = { isRealTime = !isRealTime },
            colors = ButtonDefaults.buttonColors(
                containerColor = PureWhite,
                contentColor = LightBlue,
                disabledContentColor = PureWhite,
                disabledContainerColor = MediumGray,
            ),
            border = if (isRealTime) BorderStroke(1.dp, LightBlue)
            else BorderStroke(1.dp, MediumGray),
        ) {
        }
        Text(
            text = if (isRealTime) "실시간" else "시간표",
            style = if (isRealTime) Typography.titleMedium.copy(
                fontSize = 12.sp,
                lineHeight = 28.sp,
                color = LightBlue
            )
            else Typography.titleMedium.copy(
                fontSize = 12.sp,
                lineHeight = 28.sp,
                color = MediumGray
            ),
        )
    }
}

@Composable
private fun calculateWeights(routeInfo: BusRouteInfo): Triple<Float, Float, Float> {
    val totalTime = (routeInfo.walkTimeStart + routeInfo.busTime + routeInfo.walkTimeEnd).toFloat()
    return Triple(
        routeInfo.walkTimeStart / totalTime,
        routeInfo.busTime / totalTime,
        routeInfo.walkTimeEnd / totalTime
    )
}
