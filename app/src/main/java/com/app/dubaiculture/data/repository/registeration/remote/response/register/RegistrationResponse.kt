package com.app.dubaiculture.data.repository.registeration.remote.response.register

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class RegistrationResponse constructor(@SerializedName(value = "Result")
                                       val registrationResponseDTO: RegistrationResponseDTO
) : BaseResponse() {


}