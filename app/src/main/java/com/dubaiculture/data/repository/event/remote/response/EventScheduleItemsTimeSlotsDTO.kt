package com.dubaiculture.data.repository.event.remote.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


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
    val timeTo: String,

    @SerializedName("SlotID")
    @Expose
    val slotId: String



):Parcelable