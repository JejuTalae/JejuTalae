package com.example.navermaptest

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun LocationTrackingScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            TopSearchBar()
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            val options = listOf(
                stringResource(R.string.location_tracking_mode_none),
                stringResource(R.string.location_tracking_mode_no_follow),
                stringResource(R.string.location_tracking_mode_follow),
                stringResource(R.string.location_tracking_mode_face),
            )
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(2) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .selectableGroup()
                    .background(Color(0xFFF2FCFF)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                options.forEachIndexed { index, text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .selectable(
                                selected = selectedOption == index,
                                onClick = { onOptionSelected(index) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedOption == index,
                            onClick = null
                        )
                        Text(
                            text = text,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            val locationTrackingMode = when (selectedOption) {
                0 -> LocationTrackingMode.None
                1 -> LocationTrackingMode.NoFollow
                2 -> LocationTrackingMode.Follow
                3 -> LocationTrackingMode.Face
                else -> throw IllegalStateException()
            }
            val isCompassEnabled = when (locationTrackingMode) {
                LocationTrackingMode.Follow,
                LocationTrackingMode.Face -> true
                else -> false
            }
            val cameraPositionState = rememberCameraPositionState()
            NaverMap(
                cameraPositionState = cameraPositionState,
                locationSource = rememberFusedLocationSource(
                    isCompassEnabled = isCompassEnabled,
                ),
                properties = MapProperties(
                    locationTrackingMode = locationTrackingMode,
                    isTransitLayerGroupEnabled = true
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
            )
        }
    } 
}
