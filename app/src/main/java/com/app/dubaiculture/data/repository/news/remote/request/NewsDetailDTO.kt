package com.app.dubaiculture.data.repository.news.remote.request

data class NewsDetailDTO(
    val BlockQuoteDTO: List<BlockQuoteDTO>,
    val Description: String,
    val ID: String,
    val Image: String,
    val MoreDetailDTO: List<MoreDetailDTO>,
    val PostedDate: String,
    val RelatedData: List<LatestNewsDTO>,
    val Tags: List<String>,
    val Title: String,
    val TwoColumnImageModuleDTO: List<TwoColumnImageModuleDTO>
)