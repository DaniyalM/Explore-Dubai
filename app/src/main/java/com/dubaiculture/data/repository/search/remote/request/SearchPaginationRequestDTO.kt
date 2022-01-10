package com.dubaiculture.data.repository.search.remote.request

data class SearchPaginationRequestDTO(
    val PageNo: Int? = 0,
    val PageSize: Int? = 0,
    val Keyword: String?,
    val Filter: String?,
    val Category: String?,
    val Culture: String?,
    val Sort: String?,
    val IsOld: Boolean?
)