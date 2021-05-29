package com.app.dubaiculture.data.repository.explore.local.models

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
    val pin : String? = null,
    ): Parcelable