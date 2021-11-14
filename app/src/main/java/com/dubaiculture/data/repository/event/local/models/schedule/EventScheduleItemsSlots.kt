package com.dubaiculture.data.repository.event.local.models.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventScheduleItemsSlots(
    val     timeFrom : String ? =null,
    val timeTo : String ?= null,
    val summary : String ? =null,
    val slotId : String ? =null
):Parcelable{
    override fun toString(): String {
        return timeFrom.toString()+ " "+ timeTo
    }
}