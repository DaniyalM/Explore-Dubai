package com.app.neomads.data.repository.registration.remote.response.register

import com.google.gson.annotations.SerializedName


class RegistrationResponseDTO(

    @SerializedName(value = "UserId")
    val userId: String
)