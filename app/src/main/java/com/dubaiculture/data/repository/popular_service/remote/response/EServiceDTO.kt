package com.dubaiculture.data.repository.popular_service.remote.response

data class EServiceDTO(
    val Category: String,
    val ID: String,
    val Summary: String,
    val CategoryID: String,
    val Title: String,
    val StartServiceUrl: String?,
    val FormName: String?,
    val FormSubmitURL: String?
)