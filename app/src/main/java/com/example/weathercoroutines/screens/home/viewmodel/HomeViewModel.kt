package com.example.weathercoroutines.screens.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.maps.AddressLocationManager
import com.example.domain.model.Address
import com.example.domain.usecase.ClearCacheUseCase
import com.example.domain.usecase.GetWeatherUseCase
import com.example.weathercoroutines.model.toUiModel
import com.example.weathercoroutines.screens.home.HomeState
import com.example.weathercoroutines.utils.round
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val addressLocationManager: AddressLocationManager
) : ViewModel() {

    private val _liveData: MutableLiveData<HomeState> = MutableLiveData()
    val liveData: LiveData<HomeState> = _liveData

    var loading: Channel<Boolean> = Channel()
        private set

    fun getWeatherByCoordinates(
        latitude: Double?,
        longitude: Double?
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            loading.send(true)

            runCatching {
                getWeatherUseCase(
                    latitude = latitude?.div(1.0)?.round(2) ?: 0.0,
                    longitude = longitude?.div(1.0)?.round(2) ?: 0.0
                )?.toUiModel()
            }.onSuccess { uiModel ->
                _liveData.postValue(HomeState.SuccessWeather(uiModel))
            }.onFailure { error ->
                _liveData.postValue(HomeState.Error(error))
            }

            loading.send(false)
        }
    }


    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {

            loading.send(true)

            runCatching {
                addressLocationManager.search(query)
            }.onSuccess { result ->
                _liveData.postValue(HomeState.SuccessAddresses(result))
            }.onFailure { error ->
                _liveData.postValue(HomeState.Error(error))
            }

            loading.send(false)
        }
    }

    fun getPlaceDetails(address: Address) {
        viewModelScope.launch(Dispatchers.IO) {

            loading.send(true)

            runCatching {
                addressLocationManager.fetchAddressPlaceDetails(address)
            }.onSuccess { address ->
                _liveData.postValue(HomeState.UpdateAddress(address))
            }.onFailure { error ->
                _liveData.postValue(HomeState.Error(error))
            }

            loading.send(false)
        }
    }
}