package com.example.therastreator.network

import com.example.therastreator.data.LocationJson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


private const val BASE_URL =
    "http://192.168.0.109:8080"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface SendAPIService {
    @POST("location")
    suspend fun postLocation(@Body body: LocationJson): String
}

object SendApi {
    val retrofitService: SendAPIService by lazy {
        retrofit.create(SendAPIService::class.java)
    }
}