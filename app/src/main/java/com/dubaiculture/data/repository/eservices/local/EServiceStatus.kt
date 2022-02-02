package com.dubaiculture.data.repository.eservices.local

class EServiceStatus(
    val id: String,
    val title: String,
    val categoryID: String,
    val category: String,
    val summary: String,
    val isFavourite: Boolean,
    val startServiceText: String,
    val startServiceUrl: String,
    val formName: String,
    val formSubmitURL: String,
    val request: EServiceStatusDetails
)

class EServiceStatusDetails(
    val id: String,
    val dateTime: String,
    val status: Boolean
)