package com.dubaiculture.data.repository.user.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class RefreshTokenResponse constructor(@SerializedName(value = "Result") val refreshTokenResponseDTO: RefreshTokenResponseDTO) :
    BaseResponse() {

    }