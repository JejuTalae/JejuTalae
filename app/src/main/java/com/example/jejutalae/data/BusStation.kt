package com.example.jejutalae.data

data class BusStation(
    val name: String,
    val direction: String,
    val busList: List<Bus>
)

val BusStationList: List<BusStation> = listOf(
    BusStation("제주시 버스터미널", "시청방면", listOf(bus_301_JBT, bus_424_JBT, bus_221_JBT)),
    BusStation("제주시청", "(아라방면)", listOf(bus_301_JCH, bus_424_JCH, bus_221_JCH))
        )