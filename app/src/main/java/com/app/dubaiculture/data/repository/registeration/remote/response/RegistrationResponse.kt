package com.app.neomads.data.repository.registration.remote.response.register

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class RegistrationResponse constructor(
    @SerializedName(value = "Value")
    val registrationResponseDTO: RegistrationResponseDTO) : BaseResponse() {
}