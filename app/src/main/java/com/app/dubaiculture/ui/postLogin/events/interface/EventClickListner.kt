package com.app.dubaiculture.ui.postLogin.events.`interface`

import android.widget.CheckBox
import com.app.dubaiculture.data.repository.event.local.models.Events

interface EventClickListner {
    fun checkFavListener(checkbox: CheckBox, pos: Int, isFav: Boolean, itemId: String)
    fun rowClickHandler(events: Events)

}