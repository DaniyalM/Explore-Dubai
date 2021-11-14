package com.dubaiculture.data.repository.user.remote.response

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponseDTO(
    @SerializedName(value = "Token")
    var token: String,
    @SerializedName(value = "RefreshToken")
    var refreshToken: String
    )