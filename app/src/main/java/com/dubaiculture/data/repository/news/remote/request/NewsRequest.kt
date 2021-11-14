package com.dubaiculture.data.repository.news.remote.request

data class NewsRequest(
    var id: String? = null,
    var pageNumber: Int = 0,
    var pageSize: Int = 3,
    var culture: String = "en",

    )
