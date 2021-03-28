package com.app.dubaiculture.data.repository.event.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EventScheduleItemsDTO(
    @SerializedName("Date")
    @Expose
    val date: String,
    @SerializedName("EventScheduleItemsTimeSlots")
    @Expose
    val eventScheduleItemsTimeSlots: ArrayList<EventScheduleItemsTimeSlotsDTO>
)