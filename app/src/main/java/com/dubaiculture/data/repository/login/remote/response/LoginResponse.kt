package com.dubaiculture.data.repository.login.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class LoginResponse constructor(@SerializedName(value = "Result") val loginResponseDTO: LoginResponseDTO) :
    BaseResponse() {
}