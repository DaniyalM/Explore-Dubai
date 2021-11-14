package com.dubaiculture.data.repository.popular_service.remote.response

data class RequiredDocumentDTO(
    val RequiredDocuments: List<String>?= mutableListOf(),
    val RequiredDocumentsTitle: String?
)