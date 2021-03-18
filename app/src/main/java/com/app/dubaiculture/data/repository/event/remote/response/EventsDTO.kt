package com.app.dubaiculture.data.repository.event.remote.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventsDTO(
    @SerializedName("ID")
    @Expose
    var id: String? = null,

    @SerializedName("Title")
    @Expose
    var title: String? = null,

    @SerializedName("Category")
    @Expose
    var category: String? = null,

    @SerializedName("Image")
    @Expose
    var image: String? = null,

    @SerializedName("FromDate")
    @Expose
    var fromDate: String? = null,

    @SerializedName("FromMonthYear")
    @Expose
    var fromMonthYear: String? = null,

    @SerializedName("FromTime")
    @Expose
    var fromTime: String? = null,

    @SerializedName("FromDay")
    @Expose
    var fromDay: String? = null,

    @SerializedName("ToDate")
    @Expose
    var toDate: String? = null,

    @SerializedName("ToMonthYear")
    @Expose
    var toMonthYear: String? = null,

    @SerializedName("ToTime")
    @Expose
    var toTime: String? = null,

    @SerializedName("ToDay")
    @Expose
    var toDay: String? = null,

    @SerializedName("LocationTitle")
    @Expose
    var locationTitle: String? = null,
    @SerializedName("Location")
    @Expose
    var location: String? = null,

    @SerializedName("Type")
    @Expose
    var type: String? = null,

    @SerializedName("Latitude")
    @Expose
    var latitude: String? = null,

    @SerializedName("Longitude")
    @Expose
    var longitude: String? = null,

    @SerializedName("IsFavourite")
    @Expose
    var isFavourite: Boolean = false,
    @SerializedName("Color")
    @Expose
    var color: Int?=null,
    @SerializedName("DateTo")
    @Expose
    var dateTo: String = "",
    @SerializedName("DateFrom")
    @Expose
    var dateFrom: String = "",

    ) : Parcelable