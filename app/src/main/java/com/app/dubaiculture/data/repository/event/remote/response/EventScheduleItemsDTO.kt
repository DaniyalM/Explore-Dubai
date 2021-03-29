package com.app.dubaiculture.data.repository.event.remote.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class EventScheduleItemsDTO(
    @SerializedName("Date")
    @Expose
    val date: String,
    @SerializedName("EventScheduleItemsTimeSlots")
    @Expose
    val eventScheduleItemsTimeSlots: List<EventScheduleItemsTimeSlotsDTO>
):Parcelable