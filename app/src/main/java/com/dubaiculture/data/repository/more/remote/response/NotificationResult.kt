package com.dubaiculture.data.repository.more.remote.response

import com.dubaiculture.data.repository.more.remote.response.notification.NotificationDTO

data class NotificationResult(
    val Notifications: List<NotificationDTO> = emptyList(),
    val Count: Int,
)
