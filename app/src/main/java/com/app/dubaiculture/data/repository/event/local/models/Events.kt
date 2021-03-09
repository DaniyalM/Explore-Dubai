package com.app.dubaiculture.data.repository.event.local.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Events(
    var id: String? = null,
    var title: String? = null,
    var category: String? = null,
    var image: String? = null,
    var fromDate: String? = null,
    var fromMonthYear: String? = null,
    var fromTime: String? = null,
    var fromDay: String? = null,
    var toDate: String? = null,
    var toMonthYear: String? = null,
    var toTime: String? = null,
    var toDay: String? = null,
    var type: String? = null,
    ): Parcelable





