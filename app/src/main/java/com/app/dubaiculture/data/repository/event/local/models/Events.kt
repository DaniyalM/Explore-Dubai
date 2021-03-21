package com.app.dubaiculture.data.repository.event.local.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Events(
    var id: String? = "",
    var title: String? = "",
    var category: String? = "",
    var image: String? = "",
    var fromDate: String? = "",
    var fromMonthYear: String? = "",
    var fromTime: String? = "",
    var fromDay: String? = "",
    var toDate: String? = "",
    var toMonthYear: String? = "",
    var toTime: String? = "",
    var toDay: String? = "",
    var type: String? = "",
    var locationTitle: String? = "",
    var location: String? = "",
    var longitude: String? = "",
    var latitude: String? = "",
    var isFavourite: Boolean = false,
    var color: String ?= null,
    var dateTo: String = "",
    var dateFrom: String = "",
    var distance: Double = 0.0,


    ) : Parcelable





