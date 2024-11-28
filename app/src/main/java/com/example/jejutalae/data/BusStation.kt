package com.example.jejutalae.data

import com.naver.maps.geometry.LatLng

data class BusStation(
    val name: String,
    val direction: String,
    val busList: List<Bus>,
)

data class MarkerData(
    val position:LatLng,
    val busStation:BusStation,
)

val BusStationList: List<BusStation> = listOf(
    BusStation("제주시 버스터미널", "시청방면", listOf(bus_301_JBT, bus_424_JBT, bus_221_JBT)),
    BusStation("제주시청", "(아라방면)", listOf(bus_301_JCH, bus_424_JCH, bus_221_JCH))
        )

// List of Marker Data
val markerDataList = listOf(
    MarkerData(
        position = LatLng(33.5104, 126.4914),
        busStation = BusStationList[0]
    ),
    MarkerData(
        position = LatLng(33.4996, 126.5312),
        busStation = BusStationList[1]
    )
)