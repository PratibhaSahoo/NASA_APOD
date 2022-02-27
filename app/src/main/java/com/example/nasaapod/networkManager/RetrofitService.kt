package com.example.nasaapod.networkManager

import com.example.nasaapod.model.Apod
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("planetary/apod")
    suspend fun getApodByDate(@Query("date") date: String, @Query("api_key") APIKEY: String) : Response<Apod>
}