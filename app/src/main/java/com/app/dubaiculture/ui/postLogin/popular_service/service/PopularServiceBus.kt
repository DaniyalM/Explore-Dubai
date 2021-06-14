package com.app.dubaiculture.ui.postLogin.popular_service.service

sealed class PopularServiceBus {
    class HeaderItemClick(val position: Int) : PopularServiceBus()
}
