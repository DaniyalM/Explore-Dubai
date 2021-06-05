package com.app.dubaiculture.data.repository.news.remote.request

data class NewsRequest(
        var pageNumber: Int=0,
        var pageSize:Int=3,
        var culture: String = "en",

)
