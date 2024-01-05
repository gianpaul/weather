package com.example.data.datasource.local

import com.example.data.datasource.LocalDataSource
import com.example.data.datasource.local.entity.WeatherEntity

class LocalDataSourceImpl(
    private val weatherDao: WeatherDao
): LocalDataSource {

    override suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): WeatherEntity? = weatherDao.getWeatherByCoordinates(latitude, longitude)

    override suspend fun saveWeather(weatherEntity: WeatherEntity?) {
        weatherDao.saveWeather(weatherEntity)
    }

    override suspend fun clearCache() {
        weatherDao.deleteAllWeather()
    }
}