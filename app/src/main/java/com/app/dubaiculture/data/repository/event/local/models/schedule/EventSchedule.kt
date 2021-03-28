package com.app.dubaiculture.data.repository.event.local.models.schedule

data class EventSchedule(
    val id : String?=null,
    val description : String?=null,
    val eventScheduleItems : ArrayList<EventScheduleItems>
)