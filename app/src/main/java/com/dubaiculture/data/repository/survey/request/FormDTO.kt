package com.dubaiculture.data.repository.survey.request

data class FormDTO(
    val ItemId: String,
    val Culture: String,
    val FormID: String,
    val FormName: String? = null,
    val Items: List<ItemsDTO>,
    val Title: String,
    val Subtitle: String,
) {

}