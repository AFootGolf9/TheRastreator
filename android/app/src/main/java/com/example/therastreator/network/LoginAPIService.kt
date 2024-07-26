package com.example.therastreator.network

import com.example.therastreator.data.LoginJson
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

interface LoginAPIService {

    @POST("login")
    suspend fun login(@Body body: LoginJson): LoginJson

    @POST("create")
    suspend fun create(@Body body: LoginJson): LoginJson
}

object LoginApi {
    val retrofitService: LoginAPIService by lazy {
        retrofit.create(LoginAPIService::class.java)
    }
}
