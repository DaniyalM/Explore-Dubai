package com.dubaiculture.data.repository.event.remote.request

class EventFiltersRequestDTO(
    val Culture: String = "en",
    val UserID: String? = "",
    val Category: List<String> = emptyList(),
    val Keyword: String? = "",
    val Location: String? = "",
    val DateFrom: String? = "",
    val DateTo: String? = "",
    val Type: String? = "",
)

