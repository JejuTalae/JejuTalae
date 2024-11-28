package com.example.jejutalae.data

import java.time.LocalTime

data class Bus(
    val busNo: Int, // 버스 번호
    val schedule: List<LocalTime>, // 각 정류장별 도착 시간
    val busStop: List<BusStop>
)

val bus_424_JBT_schedule = listOf(
    LocalTime.of(5, 51),
    LocalTime.of(7, 27),
    LocalTime.of(9, 10),
    LocalTime.of(11, 10),
    LocalTime.of(13, 0),
    LocalTime.of(14, 50),
    LocalTime.of(16, 30),
    LocalTime.of(18, 30),
    LocalTime.of(20, 35),
)

val bus_424_JCH_schedule = listOf(
    LocalTime.of(5, 59),
    LocalTime.of(7, 35),
    LocalTime.of(9, 18),
    LocalTime.of(11, 18),
    LocalTime.of(13, 8),
    LocalTime.of(14, 58),
    LocalTime.of(16, 38),
    LocalTime.of(18, 38),
    LocalTime.of(20, 43),
)

val bus_301_JBT = Bus(301, listOf(LocalTime.of(5, 51)), busStops_301)
val bus_424_JBT = Bus(424, bus_424_JBT_schedule, busStops_424)
val bus_221_JBT = Bus(221, listOf(LocalTime.of(5, 51)), busStops_221)

val bus_301_JCH = Bus(301, listOf(LocalTime.of(5, 51)), busStops_301)
val bus_424_JCH = Bus(424, bus_424_JCH_schedule, busStops_424)
val bus_221_JCH = Bus(221, listOf(LocalTime.of(5, 51)), busStops_221)


