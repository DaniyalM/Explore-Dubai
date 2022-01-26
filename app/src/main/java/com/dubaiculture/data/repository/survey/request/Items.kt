package com.dubaiculture.data.repository.survey.request

data class Items(
    val id: String? = null,
    val question: String? = null,
    val input: String,
    val answer: String,
    val index:Int
) {

}