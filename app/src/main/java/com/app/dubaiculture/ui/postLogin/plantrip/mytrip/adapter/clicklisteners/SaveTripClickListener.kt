package com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners

import com.app.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.app.dubaiculture.data.repository.trip.local.Trip

interface SaveTripClickListener {
    fun rowClickListener(trip: Trip)
    fun rowClickListener(trip: Trip, position: Int)
}