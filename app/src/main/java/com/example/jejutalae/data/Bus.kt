package com.example.jejutalae.data

import java.time.LocalTime

data class Bus(
    val id: String, // 고유 식별자 추가
    val busNo: Int, // 버스 번호
    val schedule: List<LocalTime>, // 각 정류장별 도착 시간
    val busStop: List<BusStop>,
    val startEndStation: String
)

val bus_301_JBT_schedule = listOf(
    LocalTime.of(6, 32),
    LocalTime.of(6, 59),
    LocalTime.of(7, 29),
    LocalTime.of(8, 39),
    LocalTime.of(9, 24),
    LocalTime.of(10, 9),
    LocalTime.of(11, 9),
    LocalTime.of(12, 9),
    LocalTime.of(13, 19),
    LocalTime.of(14, 54),
    LocalTime.of(15, 54),
    LocalTime.of(16, 54),
    LocalTime.of(17, 29),
    LocalTime.of(18, 9),
    LocalTime.of(18, 59),
    LocalTime.of(19, 19),
    LocalTime.of(19, 59),
    LocalTime.of(20, 54),
    LocalTime.of(21, 54),
)

val bus_301_DGY_schedule = bus_301_JBT_schedule.map { it.plusMinutes(6) }



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

val bus_221_JBT_schedule = listOf(
    LocalTime.of(6, 0),
    LocalTime.of(7, 5),
    LocalTime.of(8, 15),
    LocalTime.of(9, 30),
    LocalTime.of(10, 50),
    LocalTime.of(11, 30),
    LocalTime.of(12, 50),
    LocalTime.of(13, 30),
    LocalTime.of(14, 50),
    LocalTime.of(15, 30),
    LocalTime.of(16, 40),
    LocalTime.of(17, 15),
    LocalTime.of(17, 50),
    LocalTime.of(19, 0),
    LocalTime.of(20, 10),
    LocalTime.of(21, 30),
)
val bus_221_DGY_schedule = bus_221_JBT_schedule.map { it.plusMinutes(6) }

val bus_301_JBT = Bus("bus_301_JBT",301, bus_301_JBT_schedule, busStops_301, "번대동<->신사동(종점)")
val bus_424_JBT = Bus("bus_424_JBT", 424,bus_424_JBT_schedule, busStops_424, "제주버스터미널(남)<->제주버스터미널(북)")
val bus_221_JBT = Bus("bus_221_JBT", 221,bus_221_JBT_schedule, busStops_221, "제주버스터미널<->제주민속촌")

val bus_424_JCH = Bus("bus_424_JCH",424,bus_424_JCH_schedule, busStops_424, "제주버스터미널(남)<->제주버스터미널(북)")

val bus_301_DGY = Bus("bus_301_DGY",301, bus_301_DGY_schedule, busStops_301, "번대동<->신사동(종점)")
val bus_221_DGY = Bus("bus_221_DGY",221, bus_221_DGY_schedule, busStops_221, "제주버스터미널<->제주민속촌")