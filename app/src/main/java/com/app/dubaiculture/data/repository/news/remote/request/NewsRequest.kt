package com.app.dubaiculture.data.repository.news.remote.request

data class NewsRequest(
        val pageNumber: Int,
        val pageSize:Int,
        val culture: String = "en",

)
