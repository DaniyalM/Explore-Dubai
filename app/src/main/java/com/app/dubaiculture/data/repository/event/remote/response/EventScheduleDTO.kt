package com.app.dubaiculture.data.repository.event.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EventScheduleDTO (
    @SerializedName("ID")
    @Expose
    val id: String,
    @SerializedName("Description")
    @Expose
    val description: String,
    @SerializedName("EventScheduleItems")
    @Expose
    val eventScheduleItems: ArrayList<EventScheduleItemsDTO>


        )