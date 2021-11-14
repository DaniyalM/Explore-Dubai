package com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners

import com.dubaiculture.data.repository.trip.local.Duration

interface DateClickListener {
    fun rowClickListener(duration: Duration)
    fun rowClickListener(duration: Duration, position: Int)
}