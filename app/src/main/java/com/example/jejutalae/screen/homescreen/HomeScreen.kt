package com.example.jejutalae.screen.homescreen

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jejutalae.R
import com.example.jejutalae.data.BusStation
import com.example.jejutalae.data.markerDataList
import com.example.jejutalae.ui.theme.AlmostWhite
import com.example.jejutalae.ui.theme.LightBlue
import com.example.jejutalae.ui.theme.SoftBlue
import com.example.jejutalae.ui.theme.Typography
import com.example.jejutalae.viewmodel.HomeViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: HomeViewModel = viewModel()
    val bottomSheetState = rememberBottomSheetScaffoldState(
        SheetState(
            skipHiddenState = true,
            skipPartiallyExpanded = false,
            initialValue = SheetValue.PartiallyExpanded
        )
    )

    val coroutineScope = rememberCoroutineScope()

    // 다이얼로그 표시 여부
    var isDialogVisible by remember { mutableStateOf(false) }

    // ViewModel의 선택된 시간 상태를 관찰
    val selectedTime by viewModel.selectedTime.collectAsState()

    val selectedBusStation = remember { mutableStateOf<BusStation?>(null) }

    BottomSheetScaffold(scaffoldState = bottomSheetState,
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopSearchBar(modifier = Modifier.statusBarsPadding(),
                text1 = viewModel.text1,
                onText1Change = { viewModel.text1 = it },
                text2 = viewModel.text2,
                onText2Change = { viewModel.text2 = it }
            )
        },
        sheetContent = {
            BottomSheetContent(
                navController = navController,
                busStation = selectedBusStation.value
            )
        },
        sheetPeekHeight = 170.dp
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(2) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {isDialogVisible = true})
                    .background(color = SoftBlue),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedTime?.let {
                        // 선택된 시간이 있으면 표시
                        "${it.format(DateTimeFormatter.ofPattern("a h시 mm분"))} "
                    } ?: "시간을 선택해주세요",
                    style = Typography.labelSmall.copy(color = LightBlue),
                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp, start = 5.dp))
                Text(
                    text = selectedTime?.let {
                        // 선택된 시간이 있으면 표시
                        "출발 "
                    } ?: "",
                    style = Typography.labelSmall.copy(color = Color.Black),
                    modifier = Modifier.padding(top =12.dp, bottom = 12.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.triangle),
                    contentDescription = "triangle",
                    modifier = Modifier.size(21.dp),
                    tint = Color.Unspecified
                )
            }

            val locationTrackingMode: LocationTrackingMode = when (selectedOption) {
                0 -> LocationTrackingMode.None
                1 -> LocationTrackingMode.NoFollow
                2 -> LocationTrackingMode.Follow
                3 -> LocationTrackingMode.Face
                else -> throw IllegalStateException()
            }
            val isCompassEnabled = when (locationTrackingMode) {
                LocationTrackingMode.Follow, LocationTrackingMode.Face -> true

                else -> false
            }
            val jeju = LatLng(33.4996, 126.5312)
            val cameraPositionState: CameraPositionState = rememberCameraPositionState {
                // 카메라 초기 위치를 설정합니다.
                position = CameraPosition(jeju, 14.0)
            }
            NaverMap(
                cameraPositionState = cameraPositionState,
                locationSource = rememberFusedLocationSource(
                    isCompassEnabled = isCompassEnabled,
                ),
                properties = MapProperties(
                    locationTrackingMode = locationTrackingMode, isTransitLayerGroupEnabled = true
                ),
                uiSettings = MapUiSettings(
                    isLocationButtonEnabled = true,
                    isCompassEnabled = isCompassEnabled,
                ),
                 onOptionChange = {
                    cameraPositionState.locationTrackingMode?.let {
                        onOptionSelected(it.ordinal)
                    }
                },
            ) {
                for (markerData in markerDataList) {
                    Marker(
                        state = rememberMarkerState(position = markerData.position),
                        icon = OverlayImage.fromResource(R.drawable.marker),
                        width = 45.dp,
                        height = 45.dp,
                        onClick = {
                            selectedBusStation.value = markerData.busStation
                            coroutineScope.launch {
                                bottomSheetState.bottomSheetState.expand()
                            }
                            true
                        }
                    )
                }
            }
            if (isDialogVisible) {
                    DialExample(
                        onConfirm = { time ->
                            // 선택된 시간을 ViewModel에 저장
                            viewModel.updateSelectedTime(time)
                            isDialogVisible = false
                        },
                        onDismiss = {
                            isDialogVisible = false
                        }
                    )
                }
        }
    }
}

@Composable
fun BottomSheetContent(modifier: Modifier = Modifier, navController: NavHostController, busStation: BusStation?) {
    Column(
        modifier = modifier.fillMaxWidth().background(Color.White), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(busStation?.name ?: "정류소 선택하기", style = Typography.titleLarge)
        Spacer(modifier = Modifier.height(10.dp))
        Text(busStation?.direction ?: "마커를 클릭해주세요", style = Typography.labelSmall)
        Spacer(modifier = Modifier.height(15.dp))
        Row() {
            Button(
                onClick = { navController.navigate("busStop") },
                border = BorderStroke(2.dp, LightBlue),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Text("  출발  ", style = Typography.labelSmall.copy(color = LightBlue))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = { navController.navigate("busSchedule") },
                border = BorderStroke(2.dp, LightBlue),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBlue
                )
            ) {
                Text("  도착  ", style = Typography.labelSmall.copy(color = Color.White))
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
    Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
        busStation?.busList?.forEach { bus ->
            BusStationCard(bus = bus)
            Box(modifier = Modifier.fillMaxWidth().background(AlmostWhite)) {
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun TopSearchBar(modifier: Modifier = Modifier,
                 text1: String,
                 onText1Change: (String) -> Unit,
                 text2: String,
                 onText2Change: (String) -> Unit) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RectangleShape,
                color = Color(0x44000000),
                blur = 4.dp,
                offsetX = (-1).dp,
                offsetY = 4.dp,
                spread = 0.dp
            )
            .background(color = Color(0xFFFFFFFF), shape = RectangleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            val temp = text1
            onText1Change(text2)
            onText2Change(temp)
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.group_4_2x),
                contentDescription = null
            )
        }
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            TextField(value = text1,
                onValueChange = onText1Change,
                shape = RoundedCornerShape(10.dp), // 네 모서리 둥글게 설정
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF8F8F8),
                    unfocusedContainerColor = Color(0xFFF8F8F8),
                    disabledContainerColor = Color(0xFFF8F8F8),
                    focusedIndicatorColor = Color.Unspecified,
                    unfocusedIndicatorColor = Color.Unspecified,
                    disabledIndicatorColor = Color.Unspecified,
                ),
                textStyle = Typography.labelSmall.copy(color = Color.Black),
                trailingIcon = {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.delevesvg),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable {
                                onText1Change("")
                            })
                })
            Spacer(modifier = Modifier.height(10.dp))
            TextField(value = text2,
                onValueChange = onText2Change,
                shape = RoundedCornerShape(10.dp), // 네 모서리 둥글게 설정
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF8F8F8),
                    unfocusedContainerColor = Color(0xFFF8F8F8),
                    disabledContainerColor = Color(0xFFF8F8F8),
                    focusedIndicatorColor = Color.Unspecified,
                    unfocusedIndicatorColor = Color.Unspecified,
                    disabledIndicatorColor = Color.Unspecified,
                ),
                textStyle = Typography.labelSmall.copy(color = Color.Black),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "My Icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                })
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

//복사한 코드(이해할 필요X)
fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp
) = this.drawBehind {

    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint()
    paint.color = color

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}
