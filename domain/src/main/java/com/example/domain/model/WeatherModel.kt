package com.example.domain.model

data class WeatherModel(
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
