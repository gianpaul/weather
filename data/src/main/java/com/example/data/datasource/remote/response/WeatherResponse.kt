package com.example.data.datasource.remote.response

import com.example.data.datasource.local.entity.WeatherEntity
import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    val current: Current,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int
)


fun WeatherResponse.toEntity() = WeatherEntity(
    this.lat,
    this.lon,
    this.timezone,
    this.current.clouds,
    this.current.humidity,
    this.current.pressure,
    this.current.sunrise,
    this.current.sunset,
    this.current.temp,
    this.current.uvi,
    this.current.visibility
)