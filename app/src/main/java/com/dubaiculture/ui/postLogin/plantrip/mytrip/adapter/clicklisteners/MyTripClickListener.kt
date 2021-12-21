package com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners

import com.dubaiculture.data.repository.trip.local.Duration
import com.dubaiculture.data.repository.trip.local.EventsAndAttraction

interface MyTripClickListener {
    fun rowClickListener(eventAttraction: EventsAndAttraction)
    fun rowClickListener(eventAttraction: EventsAndAttraction, position: Int)
}