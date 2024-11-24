package com.example.jejutalae.screen.busschedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jejutalae.R
import com.example.jejutalae.ui.theme.BusBlue
import com.example.jejutalae.ui.theme.Gray200
import com.example.jejutalae.ui.theme.Gray600

@Composable
fun BusScheduleScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xFFFFFFFF)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(30.dp))
            }
            Text(text = "버스 경로", fontSize = 25.sp)
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(30.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(10.dp))
        Divider()

        BusScheduleCard()
    }
}

@Composable
fun BusScheduleCard() {
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
                text = "1시간",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "오전 7:00 ~ 오전 9:00",
                style = MaterialTheme.typography.bodyLarge,
                color = Gray600
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
                Box(
                    modifier = Modifier.weight(0.2f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "도보",
                        tint = Gray600,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Box(
                    modifier = Modifier.weight(0.6f),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "버스",
                            tint = BusBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "301",
                            color = BusBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Box(
                    modifier = Modifier.weight(0.2f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "도보",
                        tint = Gray600,
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
                // 전체 배경 (회색 둥근 바)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Gray200,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(0.2f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "5분",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Gray600
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.6f))
                        Box(
                            modifier = Modifier.weight(0.2f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "5분",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Gray600
                            )
                        }
                    }
                }

                // 중앙 버스 시간 (파란색 바)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(30.dp)
                        .align(Alignment.Center)
                        .background(
                            color = BusBlue,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "50분",
                        color = Color.White
                    )
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
                            color = BusBlue,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "301",
                        color = BusBlue,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 시간
                Text(
                    text = "50분",
                    color = BusBlue,
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
                                .background(BusBlue, CircleShape)
                        )
                        // 연결선
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .weight(1f)
                                .background(BusBlue)
                        )
                        // 하단 점
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(BusBlue, CircleShape)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // 정류장 정보
                    Column(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "제주 버스 터미널",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Column(
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = "오전 07:10",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Row {
                                Text(
                                    text = "오전 7:00로 부터 ",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Gray600
                                )
                                Text(
                                    text = "10분 후 도착",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Red
                                )
                            }
                        }

                        Text(
                            text = "제주 시청",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
