package com.example.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.WeatherModel

@Entity(tableName = "weather")
data class WeatherEntity(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val clouds: Int,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)

fun WeatherEntity.toDomainModel() = WeatherModel(
    this.id,
    this.lat,
    this.lon,
    this.timezone,
    this.clouds,
    this.humidity,
    this.pressure,
    this.sunrise,
    this.sunset,
    this.temp,
    this.uvi,
    this.visibility,
)
