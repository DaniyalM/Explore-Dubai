package com.app.dubaiculture.data.repository.search.local

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
    val isPage: Boolean
)