package com.app.dubaiculture.ui.postLogin.home.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseViewModel

class HomeViewModel @ViewModelInject constructor(
    application: Application
) : BaseViewModel(application) {

    val tabDetails = mutableListOf(
        Pair(R.string.explore, R.drawable.feeds),
        Pair(R.string.events, R.drawable.marketplace),
        Pair(R.string.attractions, R.drawable.forums),
        Pair(R.string.more, R.drawable.notification)
    )

    init {

    }

}