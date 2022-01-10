package com.dubaiculture.data.repository.search.local

data class SearchResultItemDTO(
    val CreationDate: String?,
    val Description: String?,
    val DetailPageUrl: String?,
    val Image: String?,
    val ItemDate: String?,
    val Tags_DropLink: List<String>?,
    val Title: String?,
    val Type: String?,
    val TypeEnum: String?,
    val isPage: Boolean?,
    val ID: String?,
    val Subtitle: String?
)