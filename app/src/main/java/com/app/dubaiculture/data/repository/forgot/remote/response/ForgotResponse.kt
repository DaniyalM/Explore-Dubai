package com.app.dubaiculture.data.repository.forgot.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class ForgotResponse constructor(@SerializedName(value = "Result") val forgotResponseDTO: ForgotResponseDTO) :
    BaseResponse() {
}