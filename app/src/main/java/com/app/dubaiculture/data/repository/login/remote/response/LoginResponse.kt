package com.app.dubaiculture.data.repository.login.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class LoginResponse constructor(@SerializedName(value = "Result") val loginResponseDTO: LoginResponseDTO) :
    BaseResponse() {
}