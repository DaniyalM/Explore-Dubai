package com.app.dubaiculture.data.repository.setpassword.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class SetPasswordResponse constructor(@SerializedName(value= "Result") val setPasswordResponseDTO: SetPasswordResponseDTO):BaseResponse() {
}