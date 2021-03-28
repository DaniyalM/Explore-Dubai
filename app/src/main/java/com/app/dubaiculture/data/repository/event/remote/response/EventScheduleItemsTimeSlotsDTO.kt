package com.app.dubaiculture.data.repository.event.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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

)