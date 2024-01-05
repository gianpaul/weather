package com.example.weathercoroutines.maps

import android.content.Context
import com.example.data.utils.AppConfig
import com.example.domain.maps.AddressErrorCode
import com.example.domain.maps.AddressException
import com.example.domain.maps.AddressLocationManager
import com.example.domain.model.Address
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.ktx.api.net.awaitFetchPlace
import com.google.android.libraries.places.ktx.api.net.awaitFindAutocompletePredictions
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class PlacesAddressLocationManager @Inject constructor(
    context: Context
) : AddressLocationManager {

    private var token: AutocompleteSessionToken? = null
    private var placesClient: PlacesClient

    init {
        Places.initialize(context, AppConfig.MAPS_API_KEY)
        placesClient = Places.createClient(context)
    }

    override fun startSession() {
        token = AutocompleteSessionToken.newInstance()
    }

    override suspend fun search(query: String): List<Address> {
        try {
            val request =
                FindAutocompletePredictionsRequest.builder()
                    .setCountries("MX", "CO", "US")
                    .setTypesFilter(listOf(PlaceTypes.ADDRESS))
                    .setSessionToken(token)
                    .setQuery(query)
                    .build()
            val response = placesClient.awaitFindAutocompletePredictions(request)
            val addresses = response.autocompletePredictions.map {
                Address(
                    placeId = it.placeId,
                    address = it.getPrimaryText(null).toString(),
                    city = it.getSecondaryText(null).toString(),
                )
            }
            return addresses
        } catch (ex: Exception) {
            throw AddressException(code = AddressErrorCode.Search)
        }
    }

    override suspend fun fetchAddressPlaceDetails(receiveAddress: Address?): Address {
        try {
            val address = requireNotNull(receiveAddress)
            val placeId = address.placeId ?: return address
            val placeFields = listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )
            val request = FetchPlaceRequest.newInstance(placeId, placeFields)
            val response = placesClient.awaitFetchPlace(request)

            return address.copy(
                placeId = response.place.id.orEmpty(),
                address = response.place.address,
                latitude = response.place.latLng?.latitude,
                longitude = response.place.latLng?.longitude
            )
        } catch (ex: Exception) {
            throw AddressException(code = AddressErrorCode.Fetch)
        }
    }
}