package com.example.weathercoroutines.di

import com.example.domain.maps.AddressLocationManager
import com.example.weathercoroutines.maps.PlacesAddressLocationManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MapsModule {

    @Binds
    fun providePlacesLocationManager(
        placesAddressLocationManager: PlacesAddressLocationManager
    ): AddressLocationManager
}