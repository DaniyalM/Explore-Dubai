package com.app.dubaiculture.data.repository.search.local

data class SearchResultDTO(
    val PageCount: Int,
    val PageNo: Int,
    val PageRecordFrom: Int,
    val PageRecordTo: Int,
    val PageSize: Int,
    val TotalRecordCount: Int,
    val Items: List<SearchResultItemDTO>

)