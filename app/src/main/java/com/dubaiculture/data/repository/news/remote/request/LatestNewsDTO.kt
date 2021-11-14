package com.dubaiculture.data.repository.news.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LatestNewsDTO(
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
        var date: String? = null
)
