package com.dubaiculture.data.repository.survey.request


data class Form(
    val title: String,
    val itemId: String?,
    val subtitle: String,
    val formID: String,
    val formName: String,
    var items: List<Items>,
    var culture:String="en"
) {

}