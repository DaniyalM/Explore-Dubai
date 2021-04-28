package com.app.dubaiculture.data.repository.event.remote.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class EventScheduleDTO (
    @SerializedName("ID")
    @Expose
    val id: String,
    @SerializedName("Description")
    @Expose
    val description: String,
    @SerializedName("EventScheduleItems")
    @Expose
    val eventScheduleItems: List<EventScheduleItemsDTO>
    ):Parcelable