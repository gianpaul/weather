package com.example.data.datasource.remote

import com.example.data.datasource.RemoteDataSource
import com.example.data.datasource.remote.response.WeatherResponse
import com.example.data.utils.Result
import com.example.data.utils.safeApiCall
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
): RemoteDataSource {

    override suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): WeatherResponse? {
        val result = safeApiCall {
            weatherApiService.getWeatherByCoordinates(
                latitude = latitude,
                longitude = longitude
            )
        }

        if (result is Result.Success) return result.data
        return null
    }
}