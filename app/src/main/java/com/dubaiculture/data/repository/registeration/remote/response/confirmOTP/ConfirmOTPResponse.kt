package com.dubaiculture.data.repository.registeration.remote.response.confirmOTP

import com.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class ConfirmOTPResponse constructor(@SerializedName(value = "Result")
                                      val confirmOTPResponseDTO: ConfirmOTPResponseDTO) : BaseResponse() {
}