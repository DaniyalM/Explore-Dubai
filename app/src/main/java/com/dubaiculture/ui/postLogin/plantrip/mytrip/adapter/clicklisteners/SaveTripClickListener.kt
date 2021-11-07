package com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners

import com.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.dubaiculture.data.repository.trip.local.Trip

interface SaveTripClickListener {
    fun rowClickListener(trip: Trip)
    fun rowClickListener(trip: Trip, position: Int)
}