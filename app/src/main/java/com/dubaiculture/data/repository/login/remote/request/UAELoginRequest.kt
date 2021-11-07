package com.dubaiculture.data.repository.login.remote.request

data class UAELoginRequest(
    val token: String?="",
    val culture: String?="",
    val email :String?="",
    val password :String?="",
)
