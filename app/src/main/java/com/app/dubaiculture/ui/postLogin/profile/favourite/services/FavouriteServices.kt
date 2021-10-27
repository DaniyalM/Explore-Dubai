package com.app.dubaiculture.ui.postLogin.profile.favourite.services

sealed class FavouriteServices {
    class HeaderItemClick(val position: Int) : FavouriteServices()
}