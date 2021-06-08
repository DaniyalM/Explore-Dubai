package com.app.dubaiculture.data.repository.news.remote.request

data class NewsDetailDTO(
    val BlockQuote: List<BlockQuoteDTO> ?= emptyList(),
    val Description: String,
    val ID: String,
    val Image: String,
    val MoreDetail: List<MoreDetailDTO> ?=  emptyList(),
    val PostedDate: String,
    val RelatedData: List<LatestNewsDTO> ?=  emptyList(),
    val Tags: List<String> ?= emptyList(),
    val Title: String,
    val TwoColumnImageModuleDTO: List<TwoColumnImageModuleDTO>?=  emptyList()
)