package com.app.dubaiculture.data.repository.attraction.local.models

import android.os.Parcelable
import com.app.dubaiculture.data.repository.explore.local.models.UpComingEvents
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Attractions(
    val id: String,
    val title: String= "",
    val category: String= "",
    val IsFavourite: Boolean=false,
    var locationTitle: String? = "",
    var location: String? = "",
    var portraitImage: String? = "",
    var landscapeImage: String? = "",
    var description: String? = "",
    var startTime: String? = "",
    var endTime: String? = "",
    var endDay: String? = "",
    var startDay: String? = "",
    var color: String? = "",
    var events: ArrayList<UpComingEvents>?=null
): Parcelable
