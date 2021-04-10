package com.app.dubaiculture.ui.postLogin.events.`interface`

import android.widget.CheckBox

interface FavouriteChecker {
    fun checkFavListener(checkbox: CheckBox, pos: Int, isFav: Boolean, itemId: String)
}