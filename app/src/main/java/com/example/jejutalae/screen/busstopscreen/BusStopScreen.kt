package com.example.jejutalae.screen.busstopscreen

import BusStopList
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jejutalae.R
import com.example.jejutalae.data.Bus
import com.example.jejutalae.data.bus_221_DGY
import com.example.jejutalae.data.bus_221_JBT
import com.example.jejutalae.data.bus_301_DGY
import com.example.jejutalae.data.bus_301_JBT
import com.example.jejutalae.data.bus_424_JBT
import com.example.jejutalae.data.bus_424_JCH
import com.example.jejutalae.ui.theme.LightBlue
import com.example.jejutalae.viewmodel.BusStopViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun BusStopScreen(modifier: Modifier = Modifier, busId: String,
                  busStationName: String,navController: NavController) {
    val bus = getBusById(busId)
        ?: // 버스를 찾을 수 없는 경우 처리
        return
    val busSchedule = bus.schedule
    val busStops = bus.busStop

    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }

    Column(modifier = Modifier
        .fillMaxSize().background(Color(0xFFFFFFFF))
    ) {
        // Top Bar with Bus Number
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xFFFFFFFF)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.offset(y = 10.dp)) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(30.dp))
            }
            Text(text = "${bus.busNo}", fontSize = 25.sp)
            IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.offset(y = 10.dp)) {
                Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(30.dp))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        // Time Selection Row
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = busStationName, fontSize = 25.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(10.dp))
        ButtonGrid(times = busSchedule,
            selectedTime = selectedTime,
            onTimeSelected = { selectedTime = it })

        Spacer(modifier = Modifier.height(8.dp))

        Divider(modifier = Modifier.fillMaxWidth())
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(25.dp))
            Icon(
                painter = painterResource(id = R.drawable.timetable),
                contentDescription = "My Icon",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
            Button(
                onClick = { },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
            ),
            ) {
                Text("버스 노선 및 시간표 확인하기", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(35.dp))
            Icon(
                painter = painterResource(id = R.drawable.next),
                contentDescription = "My Icon",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
        }
        Divider(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        BusStopList(busStops = busStops)
    }
}

@Composable
fun TimeButton(time: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        /*colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) Color.Cyan else Color.LightGray
        ),*/
        shape = CircleShape,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = time, color = if (isSelected) Color.White else Color.Black)
    }
}

@Composable
fun ButtonGrid(times: List<LocalTime>,
               selectedTime: LocalTime?,
               onTimeSelected: (LocalTime) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // 한 행에 3개의 버튼을 배치
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(times.size) { index ->
            val time = times[index]
            val formattedTime = time.format(DateTimeFormatter.ofPattern("H:mm"))
            Button(
                onClick = { onTimeSelected(time) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedTime == time) LightBlue else Color.White,
                    contentColor = if (selectedTime == time) Color.White else Color.Black
                ),
                border = BorderStroke(1.dp, LightBlue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = formattedTime)
            }
        }
    }
}

fun getBusById(busId: String): Bus? {
    // 모든 버스 객체를 리스트로 만듭니다.
    val allBuses = listOf(
        bus_301_JBT,
        bus_301_DGY,
        bus_221_JBT,
        bus_221_DGY,
        bus_424_JBT,
        bus_424_JCH
        // 필요한 모든 버스 객체를 추가
    )
    return allBuses.find { it.id == busId }
}