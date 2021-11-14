package com.dubaiculture.data.repository.registeration.remote.response.register

import com.google.gson.annotations.SerializedName


class RegistrationResponseDTO(
    @SerializedName(value = "Message")
    val message: String?=null,
    @SerializedName(value = "VerificationCode")
    val verificationCode: String
)