package com.example.weathercoroutines

import android.app.Application
import com.example.data.utils.AppConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyWeatherApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AppConfig.packageName = packageName
    }
}