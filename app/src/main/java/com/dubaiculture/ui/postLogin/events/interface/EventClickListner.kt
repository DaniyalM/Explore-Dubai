package com.dubaiculture.ui.postLogin.events.`interface`

import android.widget.CheckBox
import com.dubaiculture.data.repository.event.local.models.Events

interface EventClickListner {
    fun checkFavListener(checkbox: CheckBox, pos: Int, isFav: Boolean, itemId: String)
    fun rowClickHandler(events: Events)

}