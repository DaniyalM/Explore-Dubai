package com.dubaiculture.ui.postLogin.more.adapter.clicklisteners

import com.dubaiculture.data.repository.popular_service.local.models.ServiceCategory
import com.dubaiculture.data.repository.trip.local.UsersType

interface ServicesClickListener {
    fun rowClickListener(serviceCategory: ServiceCategory)
    fun rowClickListener(serviceCategory: ServiceCategory, position: Int)
}