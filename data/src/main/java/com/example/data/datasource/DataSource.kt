package com.example.data.datasource

import com.example.data.datasource.local.entity.WeatherEntity
import com.example.data.datasource.remote.response.WeatherResponse

interface RemoteDataSource {

    suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): WeatherResponse?
}

interface LocalDataSource{

    suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): WeatherEntity?

    suspend fun getAllWeather(): List<WeatherEntity>?

    suspend fun saveWeather(weatherEntity: WeatherEntity?)

    suspend fun clearCache()
}

