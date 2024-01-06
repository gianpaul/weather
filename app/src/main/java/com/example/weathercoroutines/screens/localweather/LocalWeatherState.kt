package com.example.weathercoroutines.screens.localweather

import com.example.weathercoroutines.model.WeatherUiModel

sealed interface LocalWeatherState {
    data class SuccessWeather(val data: List<WeatherUiModel>?): LocalWeatherState
    data class Error(val throwable: Throwable): LocalWeatherState
    object CacheCleared: LocalWeatherState
}