package com.app.dubaiculture.utils

import android.content.Context
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.more.MoreModel
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
}