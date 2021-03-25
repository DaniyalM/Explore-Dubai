package com.app.dubaiculture.infrastructure

import com.app.dubaiculture.data.repository.user.local.User

data class AuthState(
    var userId: String = "",
    var isLoggedIn: Boolean = false,
    var token: String = "",
    var refreshToken: String = "",
    var isGuest: Boolean = false,
    var user: User? = null,
    var locale: String? = "en",
)