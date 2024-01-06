package com.example.weathercoroutines.model

import com.example.domain.model.WeatherModel

data class WeatherUiModel(
    val id: Long,
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
    val icon: String
)

fun WeatherModel.toUiModel() = WeatherUiModel(
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
    this.icon
)