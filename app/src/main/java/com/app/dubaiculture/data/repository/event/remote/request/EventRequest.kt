package com.app.dubaiculture.data.repository.event.remote.request

class EventRequest(
    val eventId: String? = "",
    val culture: String? = "",
    val userId: String? = "",
    val category: List<String> = emptyList(),
    val keyword: String? = "",
    val location: String? = "",
    val dateFrom: String? = "",
    val dateTo: String? = "",
    val type: String? = "",

    )