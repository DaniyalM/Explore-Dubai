package com.app.dubaiculture.ui.postLogin.plantrip.steps.step3.adapter.clicklisteners

import com.app.dubaiculture.data.repository.trip.local.LocationNearest

interface NearestLocationClickListener {
    fun rowClickListener(nearestLocation: LocationNearest)
    fun rowClickListener(nearestLocation: LocationNearest, position: Int)
}