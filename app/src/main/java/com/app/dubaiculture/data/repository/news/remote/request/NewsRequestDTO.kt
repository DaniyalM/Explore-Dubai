package com.app.dubaiculture.data.repository.news.remote.request

data class NewsRequestDTO(
        val pageNo : Int,
        val pageSize:Int,
        val culture: String = "en",
)