package com.app.dubaiculture.ui.postLogin.attractions.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.Attraction
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.app.dubaiculture.ui.base.BaseViewModel

class AttractionViewModel @ViewModelInject constructor(
    application:Application
) :BaseViewModel(application){

    val tabDetails = mutableListOf(
        Pair(R.string.explore, R.drawable.feeds),
        Pair(R.string.events, R.drawable.marketplace),
        Pair(R.string.attractions, R.drawable.forums),
        Pair(R.string.more, R.drawable.notification),
        Pair(R.string.more, R.drawable.notification),
        Pair(R.string.more, R.drawable.notification),
        Pair(R.string.more, R.drawable.notification),
        Pair(R.string.more, R.drawable.notification),
        Pair(R.string.more, R.drawable.notification)
    )

    fun getInterests(): List<Attraction> {
        val list = mutableListOf<Attraction>()
        list.add(Attraction("1".toInt(), "Music", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("2".toInt(), "Games", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("3".toInt(), "Art", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("4".toInt(), "Sport", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("5".toInt(), "Photography", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("5".toInt(), "Marketing", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("5".toInt(), "Traveling", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("5".toInt(), "Reading", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("5".toInt(), "Blogging", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("5".toInt(), "Yoga", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("5".toInt(), "Theatre", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("5".toInt(), "History", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("5".toInt(), "Social Causes", "", R.drawable.museums_icon_home, R.drawable.events))
        list.add(Attraction("5".toInt(), "Other", "", R.drawable.museums_icon_home, R.drawable.events))
        return list
    }
}