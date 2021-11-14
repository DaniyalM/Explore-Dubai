package com.dubaiculture.data.repository.login.remote.response.resendverification

import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.registeration.remote.response.register.RegistrationResponseDTO
import com.google.gson.annotations.SerializedName

class ResendVerificationResponse constructor(@SerializedName(value = "Result")
                                             val resendVerificationResponseDTO: ResendVerificationResponseDTO
) : BaseResponse() {


}