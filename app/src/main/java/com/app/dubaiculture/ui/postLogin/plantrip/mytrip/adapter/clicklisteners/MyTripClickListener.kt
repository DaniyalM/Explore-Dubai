package com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners

import com.app.dubaiculture.data.repository.trip.local.Duration
import com.app.dubaiculture.data.repository.trip.local.EventsAndAttraction

interface MyTripClickListener {
    fun rowClickListener(eventAttraction: EventsAndAttraction)
    fun rowClickListener(eventAttraction: EventsAndAttraction, position: Int)
}