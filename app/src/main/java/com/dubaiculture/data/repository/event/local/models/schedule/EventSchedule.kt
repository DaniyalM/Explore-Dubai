package com.dubaiculture.data.repository.event.local.models.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventSchedule(
    val id: String? = null,
    val description: String? = null,
    val eventScheduleItems: List<EventScheduleItems>,
) : Parcelable