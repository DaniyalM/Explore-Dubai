package com.dubaiculture.data.repository.more.remote.request

data class ShareFeedbackRequest(
    val culture: String,
    val email: String,
    val fullName: String,
    val message: String,
    val type: String,
    val subject: String
)