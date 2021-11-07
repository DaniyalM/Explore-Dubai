package com.dubaiculture.data.repository.survey.request

data class FormDTO(
    val Title: String,
    val Subtitle: String,
    val FormID: String,
    val FormName: String? = null,
    val Items: List<ItemsDTO>
) {

}