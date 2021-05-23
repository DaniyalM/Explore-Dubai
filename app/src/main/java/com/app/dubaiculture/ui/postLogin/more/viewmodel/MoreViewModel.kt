package com.app.dubaiculture.ui.postLogin.more.viewmodel

import android.app.Application
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.hilt.lifecycle.ViewModelInject
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.more.MoreModel
import com.app.dubaiculture.ui.base.BaseViewModel

class MoreViewModel @ViewModelInject constructor(application: Application) :
    BaseViewModel(application) {


    fun setupToolbarWithSearchItems(
        logoImg: ImageView,
        pin: ImageView,
        tvTitle: TextView,
        heading: String
    ) {
        logoImg.visibility = View.GONE
        pin.visibility = View.GONE
        tvTitle.visibility = View.VISIBLE
        tvTitle.text = heading
    }


    fun servicesList(): ArrayList<MoreModel> {
        val list = ArrayList<MoreModel>()
        list.add(
            MoreModel(
                R.drawable.museum_more,
                getApplication<Application>().getString(R.string.museum_services),
                R.drawable.forward_arrow,
                true
            )
        )
        list.add(
            MoreModel(
                R.drawable.public_more,
                getApplication<Application>().getString(R.string.public_libraries_services),
                R.drawable.forward_arrow,
                true
            )
        )
        list.add(
            MoreModel(
                R.drawable.renting_more,
                getApplication<Application>().getString(R.string.renting_more),
                R.drawable.forward_arrow,
                true
            )
        )
        list.add(
            MoreModel(
                R.drawable.general_more,
                getApplication<Application>().getString(R.string.general_more),
                R.drawable.forward_arrow,
                true
            )
        )
        list.add(
            MoreModel(
                R.drawable.heritage_more,
                getApplication<Application>().getString(R.string.heritage_more),
                R.drawable.forward_arrow,
                true
            )
        )

        return list
    }

    fun newsList(): ArrayList<MoreModel> {
        val list = ArrayList<MoreModel>()
        list.add(
            MoreModel(
                R.drawable.news_more,
                getApplication<Application>().getString(R.string.news_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.phone_more,
                getApplication<Application>().getString(R.string.contact_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.question_mark_more,
                getApplication<Application>().getString(R.string.help_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.mobile_more,
                getApplication<Application>().getString(R.string.related_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.noun_policy_more,
                getApplication<Application>().getString(R.string.privacy_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.privacy_more,
                getApplication<Application>().getString(R.string.terms_and_conditions),
                R.drawable.forward_arrow,
                false
            )
        )

        return list
    }



    fun settingsList(): ArrayList<MoreModel> {
        val list = ArrayList<MoreModel>()
        list.add(
            MoreModel(
                R.drawable.setting_more,
                getApplication<Application>().getString(R.string.setting_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.accessibility_more,
                getApplication<Application>().getString(R.string.accessiblitiy_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.a_more,
                getApplication<Application>().getString(R.string.switch_language),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.logout_morre,
                getApplication<Application>().getString(R.string.logout_more),
                R.drawable.forward_arrow,
                false
            )
        )

        return list
    }


}