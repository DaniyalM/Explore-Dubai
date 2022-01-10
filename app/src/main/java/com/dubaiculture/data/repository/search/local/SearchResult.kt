package com.dubaiculture.data.repository.search.local

data class SearchResult(
    val pageCount: Int,
    val pageNo: Int,
    val pageRecordFrom: Int,
    val pageRecordTo: Int,
    val pageSize: Int,
    val totalRecordCount: Int,
    val items: List<SearchResultItem>
)