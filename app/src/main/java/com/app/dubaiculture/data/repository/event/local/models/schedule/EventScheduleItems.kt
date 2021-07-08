package com.app.dubaiculture.data.repository.event.local.models.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventScheduleItems (
    val date : String ? =null,
    val eventScheduleItemsSlots : List<EventScheduleItemsSlots>?=null
):Parcelable