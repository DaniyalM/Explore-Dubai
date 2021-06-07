package com.app.dubaiculture.data.repository.news.remote.request

data class NewsRequestDTO(
    val id: String? = null,
    val pageNo: Int,
    val pageSize: Int,
    val culture: String = "en",
)