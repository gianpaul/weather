package com.example.data.repository

import com.example.data.datasource.LocalDataSource
import com.example.data.datasource.RemoteDataSource
import com.example.data.datasource.local.entity.toDomainModel
import com.example.data.datasource.remote.response.toEntity
import com.example.domain.model.WeatherModel
import com.example.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): WeatherRepository {

    override suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): WeatherModel? {
        val getLocal = localDataSource.getWeatherByCoordinates(latitude, longitude)
        if (getLocal != null ) return getLocal.toDomainModel()

        val remoteResult =  remoteDataSource.getWeatherByCoordinates(
            latitude = latitude,
            longitude = longitude
        )?.toEntity()

        localDataSource.saveWeather(remoteResult)

        return localDataSource.getWeatherByCoordinates(latitude, longitude)?.toDomainModel()
    }

    override suspend fun clearCache() {
        localDataSource.clearCache()
    }
}