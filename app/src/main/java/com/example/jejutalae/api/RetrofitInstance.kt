package com.example.jejutalae.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: BusStopApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.example.com/") // 변경할 것
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BusStopApi::class.java)
    }
}