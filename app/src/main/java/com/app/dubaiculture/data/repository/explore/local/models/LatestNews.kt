package com.app.dubaiculture.data.repository.explore.local.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LatestNews(
    @SerializedName("ID")
    @Expose
    var id: String? = null,

    @SerializedName("Title")
    @Expose
    var title: String? = null,

    @SerializedName("Image")
    @Expose
    var image: String? = null,

    @SerializedName("PostedDate")
    @Expose
    var postedDate: String? = null,

    @SerializedName("Date")
    @Expose
    var date: String? = null,
)
