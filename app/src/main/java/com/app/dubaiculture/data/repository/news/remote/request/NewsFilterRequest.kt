package com.app.dubaiculture.data.repository.news.remote.request

class NewsFilterRequest(
    val tags: List<String> = emptyList(),
    val keyword: String? = "",
    val dateFrom: String? = "",
    val dateTo: String? = "",
    val culture: String? = "",
)