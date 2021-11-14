package com.dubaiculture.data.repository.search.remote.request

data class SearchRequestDTO(
    val UserID: String = "",
    val Culture: String = ""
)

data class SearchRequest(
    val userId: String = "",
    val culture: String = ""
)