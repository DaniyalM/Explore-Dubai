package com.dubaiculture.ui.postLogin.popular_service.adapter.clicklistener

import com.dubaiculture.data.repository.popular_service.local.models.EService

interface ServiceClickListner {
    fun onServiceClick(service: EService?)
    fun onUrlClick(service: EService?)
}