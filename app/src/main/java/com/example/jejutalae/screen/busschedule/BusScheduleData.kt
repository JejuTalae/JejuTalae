package com.example.jejutalae.screen.busschedule

import java.time.Duration
import java.time.LocalTime

data class BusRouteInfo(
    val busNumber: String,
    val startStation: String,
    val endStation: String,
    val walkTimeStart: Int,
    val busTime: Int,
    val walkTimeEnd: Int,
    val departureTime: String,
    val arrivalAfterMinutes: String,
    val waitingTime: Duration,
    val baseTime: LocalTime
) {
    private val totalMinutes = waitingTime.toMinutes() + walkTimeStart + busTime + walkTimeEnd

    val totalTime: String = run {
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        buildString {
            if (hours > 0) {
                append("${hours}시간 ")
            }
            append("${minutes}분")
        }
    }

    val timeRange: String = run {
        val endTime = baseTime.plusMinutes(totalMinutes)
        "${formatTime(baseTime)} ~ ${formatTime(endTime)}"
    }

    companion object {
        fun formatTime(time: LocalTime): String {
            val hour = time.hour
            val amPm = if (hour < 12) "오전" else "오후"
            val adjustedHour = if (hour > 12) hour - 12 else if (hour == 0) 12 else hour
            return "$amPm $adjustedHour:${String.format("%02d", time.minute)}"
        }
    }
} 