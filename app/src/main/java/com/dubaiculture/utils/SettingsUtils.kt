package com.dubaiculture.utils

import android.app.Application
import android.content.Context
import com.dubaiculture.R
import com.dubaiculture.data.repository.more.MoreModel
import java.util.ArrayList

object SettingsUtils {

    fun settingsList(context: Context): ArrayList<MoreModel> {
        val list = ArrayList<MoreModel>()
        list.add(
            MoreModel(
                R.drawable.setting_more,
                context. resources.getString(R.string.setting_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.accessibility_more,
                context.   resources.getString(R.string.accessiblitiy_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.a_more,
                context.  resources.getString(R.string.english),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.logout_morre,
                context.   resources.getString(R.string.logout_more),
                R.drawable.forward_arrow,
                false
            )
        )
        return list
    }




    fun servicesList(context: Context): ArrayList<MoreModel> {
        val list = ArrayList<MoreModel>()
        list.add(
            MoreModel(
                R.drawable.museum_more,
               context.   resources.getString(R.string.museum_services),
                R.drawable.forward_arrow,
                true
            )
        )
        list.add(
            MoreModel(
                R.drawable.public_more,
               context.   resources.getString(R.string.public_libraries_services),
                R.drawable.forward_arrow,
                true
            )
        )
        list.add(
            MoreModel(
                R.drawable.renting_more,
               context.   resources.getString(R.string.renting_more),
                R.drawable.forward_arrow,
                true
            )
        )
        list.add(
            MoreModel(
                R.drawable.general_more,
               context.   resources.getString(R.string.general_more),
                R.drawable.forward_arrow,
                true
            )
        )
        list.add(
            MoreModel(
                R.drawable.heritage_more,
               context.   resources.getString(R.string.heritage_more),
                R.drawable.forward_arrow,
                true
            )
        )
        return list
    }
    fun newsList(context: Context): ArrayList<MoreModel> {
        val list = ArrayList<MoreModel>()
        list.add(
            MoreModel(
                R.drawable.news_more,
               context.   resources.getString(R.string.news_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.phone_more,
               context.   resources.getString(R.string.contact_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.question_mark_more,
               context.   resources.getString(R.string.help_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.mobile_more,
               context.   resources.getString(R.string.related_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.noun_policy_more,
               context.   resources.getString(R.string.privacy_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.privacy_more,
               context.   resources.getString(R.string.terms_and_conditions),
                R.drawable.forward_arrow,
                false
            )
        )
        return list
    }
}