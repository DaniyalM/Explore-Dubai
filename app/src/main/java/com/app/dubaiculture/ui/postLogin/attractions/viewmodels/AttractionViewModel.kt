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


    fun getInterests(): List<Attraction> {
        val list = mutableListOf<Attraction>()
        list.add(Attraction("1".toInt(), "Museum", "", R.drawable.museum, R.drawable.museum))
        list.add(Attraction("2".toInt(), "Heritage Sites", "", R.drawable.heritage, R.drawable.heritage))
        list.add(Attraction("3".toInt(), "Festivals", "", R.drawable.festival, R.drawable.festival))
        list.add(Attraction("4".toInt(), "Attractions", "", R.drawable.museums_icon_home, R.drawable.museums_icon_home))
     return list
    }
}