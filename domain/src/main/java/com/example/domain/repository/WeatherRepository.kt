package com.example.domain.repository

import com.example.domain.model.WeatherModel

interface WeatherRepository {

    suspend fun getWeatherByCoordinates (
        latitude: Double,
        longitude: Double
    ): WeatherModel?

    suspend fun clearCache()
}