package com.app.dubaiculture.data.repository.survey.request


data class Form(
    val title: String, val subtitle: String, val formID: String, val formName: String,
    val items: List<Items>
) {

}