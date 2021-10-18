package com.app.dubaiculture.data.repository.trip.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.app.dubaiculture.data.repository.login.remote.response.changepassword.ChangedPasswordResponseDTO
import com.google.gson.annotations.SerializedName

class UserTypeResponse constructor(@SerializedName(value = "Result") val userTypeResponseDTO: UserTypeResponseDTO) :
    BaseResponse() {
}