package com.app.dubaiculture.ui.postLogin.more.profile.favourite

sealed class FavouriteServices {
    class HeaderItemClick(val position: Int) : FavouriteServices()
}