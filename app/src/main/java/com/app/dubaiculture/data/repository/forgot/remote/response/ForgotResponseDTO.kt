package com.app.dubaiculture.data.repository.forgot.remote.response

import com.google.gson.annotations.SerializedName

class ForgotResponseDTO (
    @SerializedName(value = "Message")
    val message: String?=null,
    @SerializedName(value = "VerificationCode")
    val verificationCode: String
)