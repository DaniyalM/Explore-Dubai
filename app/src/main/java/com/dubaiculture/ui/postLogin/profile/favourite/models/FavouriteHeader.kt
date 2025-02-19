package com.dubaiculture.ui.postLogin.profile.favourite.models

data class FavouriteHeader(
        var id: Int? = 0,
        var title: String? = "",
        var selectedIcon: Int?,
        var unselectedIcon: Int?,
        var selectedColor: Int? = 0,
        var unSelectedColor: Int? = 0,
        var color: String? = "#582c83",
)
