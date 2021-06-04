package com.app.dubaiculture.data.repository.news.local

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LatestNews(
    var id: String? = null,
    var title: String? = null,
    var image: String? = null,
    var postedDate: String? = null,
    var date: String? = null
)
