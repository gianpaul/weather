package com.example.weathercoroutines.screens.localweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.ClearCacheUseCase
import com.example.domain.usecase.GetAllLocalWeatherUseCase
import com.example.weathercoroutines.model.toUiModel
import com.example.weathercoroutines.screens.localweather.LocalWeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalWeatherViewModel @Inject constructor(
    private val getAllLocalWeatherUseCase: GetAllLocalWeatherUseCase,
    private val clearCacheUseCase: ClearCacheUseCase
) : ViewModel() {

    private val _liveData: MutableLiveData<LocalWeatherState> = MutableLiveData()
    val liveData: LiveData<LocalWeatherState> = _liveData

    var loading: Channel<Boolean> = Channel()
        private set

    init {
        getAllLocalWeather()
    }

    private fun getAllLocalWeather() {
        viewModelScope.launch(Dispatchers.IO) {

            loading.send(true)

            runCatching {
                getAllLocalWeatherUseCase()
            }.onSuccess { weathers ->
                _liveData.postValue(LocalWeatherState.SuccessWeather(weathers?.map {
                    it.toUiModel()
                }))
            }.onFailure { error ->
                _liveData.postValue(LocalWeatherState.Error(error))
            }

            loading.send(false)
        }
    }

    fun clearCache() {

        viewModelScope.launch(Dispatchers.IO) {

            loading.send(true)

            runCatching {
                clearCacheUseCase()
            }.onSuccess {
                _liveData.postValue(LocalWeatherState.CacheCleared)
            }.onFailure { error ->
                _liveData.postValue(LocalWeatherState.Error(error))
            }

            loading.send(false)
        }
    }
}