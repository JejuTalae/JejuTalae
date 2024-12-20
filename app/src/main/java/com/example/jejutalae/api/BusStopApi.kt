package com.example.jejutalae.api

import com.example.jejutalae.data.BusStop
import retrofit2.http.GET
import retrofit2.http.Query

interface BusStopApi {
    @GET("bus_stops")
    suspend fun getBusStops(@Query("bus_number") busNumber: String): List<BusStop>
}