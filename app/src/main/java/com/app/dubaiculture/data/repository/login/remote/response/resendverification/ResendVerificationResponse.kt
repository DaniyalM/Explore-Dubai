package com.app.dubaiculture.data.repository.login.remote.response.resendverification

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.app.dubaiculture.data.repository.registeration.remote.response.register.RegistrationResponseDTO
import com.google.gson.annotations.SerializedName

class ResendVerificationResponse constructor(@SerializedName(value = "Result")
                                             val resendVerificationResponseDTO: ResendVerificationResponseDTO
) : BaseResponse() {


}