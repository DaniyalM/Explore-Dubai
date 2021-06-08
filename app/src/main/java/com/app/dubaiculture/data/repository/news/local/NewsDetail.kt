package com.app.dubaiculture.data.repository.news.local

data class NewsDetail(
    val blockQuote: List<BlockQuote> ?=  emptyList(),
    val description: String,
    val id: String,
    val image: String,
    val moreDetail: List<MoreDetail> ?=  emptyList(),
    val postedDate: String,
    val relatedData: List<LatestNews>?= emptyList(),
    val tags: List<String> ?=  emptyList(),
    val title: String,
    val twoColumnImageModule: List<TwoColumnImageModule> ?=  emptyList()
)