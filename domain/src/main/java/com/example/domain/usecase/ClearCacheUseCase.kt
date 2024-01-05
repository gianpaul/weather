package com.example.domain.usecase

import com.example.domain.repository.WeatherRepository
import javax.inject.Inject

class ClearCacheUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke() {
        weatherRepository.clearCache()
    }
}