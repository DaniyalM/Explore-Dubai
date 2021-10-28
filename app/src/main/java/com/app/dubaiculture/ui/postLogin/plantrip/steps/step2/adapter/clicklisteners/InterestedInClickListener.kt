package com.app.dubaiculture.ui.postLogin.plantrip.steps.step2.adapter.clicklisteners

import com.app.dubaiculture.data.repository.trip.local.InterestedInType

interface InterestedInClickListener {
    fun rowClickListener(interestedInType: InterestedInType)
    fun rowClickListener(interestedInType: InterestedInType, position: Int)
}