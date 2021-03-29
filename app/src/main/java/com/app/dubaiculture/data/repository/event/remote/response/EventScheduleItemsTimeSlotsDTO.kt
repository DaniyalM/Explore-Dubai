package com.app.dubaiculture.data.repository.event.remote.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class EventScheduleItemsTimeSlotsDTO(
    @SerializedName("Summary")
    @Expose
    val summary: String,
    @SerializedName("TimeFrom")
    @Expose
    val timeFrom: String,
    @SerializedName("TimeTo")
    @Expose
    val timeTo: String

):Parcelable