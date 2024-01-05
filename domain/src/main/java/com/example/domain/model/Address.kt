package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val id: String? = null,
    val address: String? = null,
    val placeId: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zipCode: String? = null,
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
): Parcelable