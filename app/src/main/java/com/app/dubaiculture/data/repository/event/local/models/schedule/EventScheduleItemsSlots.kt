package com.app.dubaiculture.data.repository.event.local.models.schedule

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventScheduleItemsSlots(
    val timeFrom : String ? =null,
    val timeTo : String ?= null,
    val summary : String ? =null
):Parcelable