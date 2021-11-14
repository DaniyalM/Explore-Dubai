package com.dubaiculture.data.repository.news.local

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LatestNews(
    var id: String? = null,
    var title: String? = null,
    var image: String? = null,
    var postedDate: String? = null,
    var date: String? = null
): Parcelable
