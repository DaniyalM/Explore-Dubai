package com.dubaiculture.data.repository.login.remote.response.changepassword

import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.login.remote.response.LoginResponseDTO
import com.google.gson.annotations.SerializedName

class ChangedPasswordResponse constructor(@SerializedName(value = "Result") val changedPasswordResponseDTO: ChangedPasswordResponseDTO) :
    BaseResponse() {
}