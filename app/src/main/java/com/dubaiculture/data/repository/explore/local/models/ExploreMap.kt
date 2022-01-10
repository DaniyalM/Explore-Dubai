package com.dubaiculture.data.repository.explore.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExploreMap(
    val id: String? = null,
    val image: String? = null,
    val title: String? = null,
    val location: String? = null,
    val lat: String? = null,
    val lng: String? = null,
    val distance: Double? = null,
    val pinInRadius: String? = null,
    val pinOutRadius: String? = null,
    var isAttraction: Boolean = false,
    var attractionCat:Int?=null
): Parcelable