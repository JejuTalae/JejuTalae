package com.example.navermaptest

import BusStopItemPreview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

@Composable
fun BusStopScreen(viewModel: BusStopViewModel = viewModel(), navController: NavController) {
    var busNumber by remember { mutableStateOf("") }
    val busStops by viewModel.busStops.observeAsState(initial = emptyList())
    val errorMessage by viewModel.errorMessage.observeAsState(initial = "")
    var selectedTime by remember { mutableStateOf("") }

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
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(30.dp))
            }
            Text(text = "520", fontSize = 25.sp)
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(30.dp))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        // Time Selection Row
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "선택된 정류소 이름", fontSize = 25.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(10.dp))
        ButtonGrid(listOf("9:20", "11:30", "13:20", "14:50", "16:10", "17:20", "18:10"))

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
                onClick = { viewModel.loadBusStops(busNumber) },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterVertically)
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

        // Error Message or Bus Stop Route
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            BusStopItemPreview()
        }
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
fun ButtonGrid(buttonTexts: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // 한 행에 3개의 버튼을 배치
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(buttonTexts.size) { index ->
            Button(
                onClick = { /* Button action */ },
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, Color(0xff41C3E7))
            ) {
                Text(text = buttonTexts[index])
            }
        }
    }
}