package com.dubaiculture.data.repository.user.remote.request

class RefreshTokenRequest(
    val token: String,
    val tokenRefresh: String
)