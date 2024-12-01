package com.example.jejutalae.screen.busschedule

data class BusRouteInfo(
    val busNumber: String,
    val startStation: String,
    val endStation: String,
    val totalTime: String,
    val timeRange: String,
    val walkTimeStart: Int,
    val busTime: Int,
    val walkTimeEnd: Int,
    val departureTime: String,
    val arrivalAfterMinutes: String
) 