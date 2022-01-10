package com.dubaiculture.data.repository.search.local

data class SearchResultItem(
    val id: String,
    val creationDate: String,
    val description: String,
    val detailPageUrl: String,
    val image: String,
    val itemDate: String,
    val tags_DropLink: List<String>,
    val title: String,
    val type: String,
    val typeEnum: String,
    val isPage: Boolean,
    val subtitle: String
)