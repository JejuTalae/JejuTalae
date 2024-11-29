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
    BusStation("제주시청", "아라방면", listOf(bus_424_JCH)),
    BusStation("동광양", "남", listOf(bus_221_DGY, bus_301_DGY))
        )

// List of Marker Data
val markerDataList = listOf(
    MarkerData(
        position = LatLng(33.499854, 126.51475),
        busStation = BusStationList[0]
    ),
    MarkerData(
        position = LatLng(33.499404, 126.52992),
        busStation = BusStationList[1]
    ),
    MarkerData(
        position = LatLng(33.502011, 126.532351),
        busStation = BusStationList[2]
)
)