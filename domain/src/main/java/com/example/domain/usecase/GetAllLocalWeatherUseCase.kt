package com.example.domain.usecase

import com.example.domain.model.WeatherModel
import com.example.domain.repository.WeatherRepository
import javax.inject.Inject

class GetAllLocalWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(): List<WeatherModel>? = weatherRepository.getAllLocalWeather()
}