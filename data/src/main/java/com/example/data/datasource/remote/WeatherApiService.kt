package com.example.data.datasource.remote

import com.example.data.datasource.remote.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    object Endpoints {
        const val ONE_CALL = "onecall"
    }

    @GET(Endpoints.ONE_CALL)
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = "minutely,hourly,daily"
    ): Response<WeatherResponse>
}