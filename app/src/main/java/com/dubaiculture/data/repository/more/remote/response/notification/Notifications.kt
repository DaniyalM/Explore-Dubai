package com.dubaiculture.data.repository.more.remote.response.notification

data class Notifications(
    val body: String,
    val dateTime: String,
    val id: String,
    val icon: String?,
    val title: String,
    val timeAgo: String,
    val bodyMarkup: String
)