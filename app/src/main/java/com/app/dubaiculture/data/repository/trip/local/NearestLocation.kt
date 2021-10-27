package com.app.dubaiculture.data.repository.trip.local

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

data class NearestLocation(
    val title: String,
    val nearestLocation: List<LocationNearest>
)

data class LocationNearest(
    val latitude: String,
    val locationId: String,
    val locationTitle: String,
    val longitude: String,
    val isChecked: Boolean
)