package com.dubaiculture.ui.postLogin.latestnews.newsfilter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val tags: List<String> = emptyList(),
    val keyword: String = "",
    val dateFrom: String = "",
    val dateTo: String = "",
):Parcelable