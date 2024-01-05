package com.example.domain.usecase

import com.example.domain.model.WeatherModel
import com.example.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): WeatherModel? {
        return weatherRepository.getWeatherByCoordinates(
            latitude = latitude,
            longitude = longitude
        )
    }
}