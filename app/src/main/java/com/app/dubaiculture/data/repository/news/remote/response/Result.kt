package com.app.dubaiculture.data.repository.news.remote.response

import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.app.dubaiculture.data.repository.news.local.NewsDetail
import com.app.dubaiculture.data.repository.news.remote.request.LatestNewsDTO
import com.app.dubaiculture.data.repository.news.remote.request.NewsDetailDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
        @SerializedName("LatestNews")
        @Expose
        var latest: List<LatestNewsDTO>,

        @SerializedName("News")
        @Expose
        var news: List<LatestNewsDTO>,

        @SerializedName("NewsDetail")
        @Expose
        var detail: NewsDetailDTO
)