package com.example.weathercoroutines.di

import android.content.Context
import com.example.domain.repository.WeatherRepository
import com.example.domain.usecase.GetWeatherUseCase
import com.example.weathercoroutines.maps.PlacesAddressLocationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetWeatherUseCase(
        weatherRepository: WeatherRepository
    ): GetWeatherUseCase = GetWeatherUseCase(weatherRepository)

    @Provides
    @Singleton
    fun providePlacesAddressLocationManager(
        @ApplicationContext context: Context
    ) = PlacesAddressLocationManager(context)
}