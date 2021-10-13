package com.app.dubaiculture.ui.postLogin.popular_service.adapter.clicklistener

import com.app.dubaiculture.data.repository.popular_service.local.models.EService

interface ServiceClickListner {
    fun onServiceClick(service: EService?)
}