package com.app.dubaiculture.ui.postLogin.latestnews.newsfilter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    var tags: MutableList<String> = mutableListOf(),
    var keyword: String = "",
    var dateFrom: String = "",
    var dateTo: String = "",
):Parcelable