package com.app.dubaiculture.ui.postLogin.home.viewmodels

import android.app.Application
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
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