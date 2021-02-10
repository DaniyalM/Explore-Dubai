package com.app.dubaiculture.data.repository.user.remote.response

import com.google.gson.annotations.SerializedName

class GuestTokenResponseDTO (
    @SerializedName(value = "Token")
    var token: String,
)
