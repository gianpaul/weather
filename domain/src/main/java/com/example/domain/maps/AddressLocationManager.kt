package com.example.domain.maps

import com.example.domain.model.Address

enum class AddressErrorCode(val messageInt: String) {
    Search("There was an error searching the location, try later"),
    Fetch("There was an error searching the location, try later"),
    Unknown("Unknown error, try again")
}

data class AddressException(
    val code: AddressErrorCode = AddressErrorCode.Unknown
) : Exception(code.messageInt)

interface AddressLocationManager {
    fun startSession()

    @Throws(AddressException::class)
    suspend fun search(query: String): List<Address>

    @Throws(AddressException::class)
    suspend fun fetchAddressPlaceDetails(receiveAddress: Address?): Address
}