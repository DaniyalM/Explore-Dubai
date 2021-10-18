package com.app.dubaiculture.data.repository.more.local

data class FaqItem(
    val id:Int?,
    val answer: String,
    val question: String,
    val count: Int?= 0,
    var is_expanded :Boolean =false
)