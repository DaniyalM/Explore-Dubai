package com.app.dubaiculture.data.repository.popular_service.remote.request

data class EServiceRequest(
    val culture: String? = "en",
    val id: String? = null,
    val fullName: String = "",
    val email: String = "",
    val comment: String = ""
)
