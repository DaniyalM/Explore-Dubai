package com.app.dubaiculture.data.repository.login.remote.response.resendverification

import com.google.gson.annotations.SerializedName

class ResendVerificationResponseDTO(
    @SerializedName(value = "Message")
    val message: String?=null,
    @SerializedName(value = "VerificationCode")
    val verificationCode: String
)