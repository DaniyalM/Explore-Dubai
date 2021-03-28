package com.app.dubaiculture.data.repository.event.local.models.schedule

data class EventScheduleItems (
    val date : String ? =null,
    val eventScheduleItemsSlots : ArrayList<EventScheduleItemsSlots>?=null
)