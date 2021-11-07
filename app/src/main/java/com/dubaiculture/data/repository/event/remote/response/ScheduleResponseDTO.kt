package com.dubaiculture.data.repository.event.remote.response

import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.data.repository.event.local.models.schedule.EventSchedule
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ScheduleResponseDTO(
    @SerializedName("Events")
    @Expose
    val events: EventsDTO,
    @SerializedName("EventSchedule")
    @Expose
    val schedule: ArrayList<EventScheduleDTO>,
)