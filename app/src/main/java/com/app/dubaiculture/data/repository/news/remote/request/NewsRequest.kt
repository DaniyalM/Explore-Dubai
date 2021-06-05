package com.app.dubaiculture.data.repository.news.remote.request

data class NewsRequest(
        val pageNumber: Int=0,
        val pageSize:Int=3,
        val culture: String = "en",

)
