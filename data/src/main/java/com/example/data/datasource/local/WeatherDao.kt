package com.example.data.datasource.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.datasource.local.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE lat = :latitude AND lon =:longitude")
    suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): WeatherEntity?

    @Query("SELECT * FROM weather")
    suspend fun getAllWeather(): List<WeatherEntity>?

    @Query("DELETE FROM weather")
    suspend fun deleteAllWeather()

    @Upsert
    suspend fun saveWeather(weather: WeatherEntity?)
}