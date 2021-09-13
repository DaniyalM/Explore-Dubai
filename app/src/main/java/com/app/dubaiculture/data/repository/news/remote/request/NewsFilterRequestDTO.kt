package com.app.dubaiculture.data.repository.news.remote.request

class NewsFilterRequestDTO(
    val Culture: String = "en",
    val Tag: List<String> = emptyList(),
    val Keyword: String? = "",
    val DateFrom: String? = "",
    val DateTo: String? = "",
)
