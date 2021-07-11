package com.app.dubaiculture.ui.postLogin.popular_service.models

data class ServiceHeader(
        var id: Int? = 0,
        var title: String = "Test",
        var selectedIcon: Int?,
        var unselectedIcon: Int?,
        var selectedColor: Int? = 0,
        var unSelectedColor: Int? = 0,
        var color: String? = "#582c83"
)
