package com.app.dubaiculture.data.repository.news.local

data class NewsDetail(
    val blockQuote: List<BlockQuote>,
    val description: String,
    val id: String,
    val image: String,
    val moreDetail: List<MoreDetail>,
    val postedDate: String,
    val relatedData: List<LatestNews>,
    val tags: List<String>,
    val title: String,
    val twoColumnImageModule: List<TwoColumnImageModule>
)