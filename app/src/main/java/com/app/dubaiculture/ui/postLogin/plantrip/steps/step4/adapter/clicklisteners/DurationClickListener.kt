package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.clicklisteners

import com.app.dubaiculture.data.repository.trip.local.Duration

interface DurationClickListener {
    fun rowClickListener(duration: Duration)
    fun rowClickListener(duration: Duration, position: Int)
}