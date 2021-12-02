package com.dubaiculture.ui.postLogin.more.about.adapter.clicklisteners

import com.dubaiculture.data.repository.more.local.Library
import com.dubaiculture.data.repository.trip.local.UsersType

interface LibraryClickListener {
    fun rowClickListener(library: Library)
    fun rowClickListener(library: Library, position: Int)
}