package com.dubaiculture.ui.postLogin.plantrip.steps.step1.adapter.clicklisteners

import com.dubaiculture.data.repository.trip.local.UsersType

interface UserTypeClickListener {
    fun rowClickListener(userType: UsersType)
    fun rowClickListener(userType: UsersType, position: Int)
}