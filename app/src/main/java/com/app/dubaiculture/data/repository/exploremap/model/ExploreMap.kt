package com.app.dubaiculture.data.repository.exploremap.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExploreMap(
    val id : String ? =null,
    val image : String? = null,
    val title : String? = null,
    val location : String? = null,
    val lat : String? = null,
    val lng : String? = null,
    val distance : Double? = null,
    val pinInRadius : String? = null,
    val pinOutRadius : String? = null
    ): Parcelable