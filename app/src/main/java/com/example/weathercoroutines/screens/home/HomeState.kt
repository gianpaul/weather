package com.example.weathercoroutines.screens.home

import com.example.domain.model.Address
import com.example.weathercoroutines.model.WeatherUiModel

sealed interface HomeState {
    data class SuccessWeather(val data: WeatherUiModel?): HomeState
    data class SuccessAddresses(val addresses: List<Address>): HomeState
    data class UpdateAddress(val address: Address?): HomeState
    data class Error(val throwable: Throwable): HomeState
}