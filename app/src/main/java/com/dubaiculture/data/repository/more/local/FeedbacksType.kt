package com.dubaiculture.data.repository.more.local

data class FeedbacksType(
    val id: String,
    val title: String
){
    override fun toString(): String {
        return title
    }
}