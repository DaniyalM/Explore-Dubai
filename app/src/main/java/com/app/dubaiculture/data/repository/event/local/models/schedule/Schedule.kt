package com.app.dubaiculture.data.repository.event.local.models.schedule

import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.response.EventsDTO

data class Schedule(
    val events  : EventsDTO?=null,
    val eventSchedule : ArrayList<EventSchedule>?=null
)