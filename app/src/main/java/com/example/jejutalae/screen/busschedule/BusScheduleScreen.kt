package com.example.jejutalae.screen.busschedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jejutalae.R
import com.example.jejutalae.ui.theme.LightBlue
import com.example.jejutalae.ui.theme.VeryLightGray
import com.example.jejutalae.ui.theme.MediumGray

@Composable
fun BusScheduleScreen(
    startStation: String,
    endStation: String
) {
    val busRoutes = listOf(
        BusRouteInfo(
            busNumber = "301",
            startStation = "제주시 버스터미널",
            endStation = "동광양",
            totalTime = "14분",
            timeRange = "오후 1:03 ~ 오후 1:17",
            walkTimeStart = 2,
            busTime = 6,
            walkTimeEnd = 6,
            departureTime = "오후 1:03",
            arrivalAfterMinutes = "27분"
        ),
        BusRouteInfo(
            busNumber = "424",
            startStation = "제주시 버스터미널",
            endStation = "제주시청",
            totalTime = "11분",
            timeRange = "오후 1:09 ~ 오후 1:20",
            walkTimeStart = 2,
            busTime = 6,
            walkTimeEnd = 3,
            departureTime = "오후 1:09",
            arrivalAfterMinutes = "33분"
        ),
        BusRouteInfo(
            busNumber = "221",
            startStation = "제주시 버스터미널",
            endStation = "동광양",
            totalTime = "14분",
            timeRange = "오후 12:44 ~ 오후 12:58",
            walkTimeStart = 2,
            busTime = 6,
            walkTimeEnd = 6,
            departureTime = "오후 12:44",
            arrivalAfterMinutes = "8분"
        )
    ).filter { route ->
        route.startStation == startStation && route.endStation == endStation
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
                textAlign = TextAlign.Center
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
                        painter = painterResource(id = R.drawable.location),
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
                            painter = painterResource(id = R.drawable.location),
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
                        painter = painterResource(id = R.drawable.location),
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
                                    text = "오후 12:36로 부터 ",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MediumGray
                                )
                                Text(
                                    text = routeInfo.arrivalAfterMinutes + " 후 도착",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Red
                                )
                            }
                        }

                        Text(
                            text = routeInfo.endStation,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
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
