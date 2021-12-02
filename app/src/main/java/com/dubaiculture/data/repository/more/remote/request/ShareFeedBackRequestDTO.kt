package com.dubaiculture.data.repository.more.remote.request

data class ShareFeedBackRequestDTO (
    val Culture: String,
    val Email: String,
    val Subject: String,
    val FullName: String,
    val Message: String,
    val Type: String
    )