package com.app.dubaiculture.data.repository.news.local

data class NewsTags(
    val tag_id: String,
    val tag_title: String,
    var isSelected : Boolean =false
)