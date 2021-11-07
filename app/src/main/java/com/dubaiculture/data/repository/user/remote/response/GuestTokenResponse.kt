package com.dubaiculture.data.repository.user.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class GuestTokenResponse(
    @SerializedName(value = "Result") val guestTokenResponseDTO: GuestTokenResponseDTO):BaseResponse()