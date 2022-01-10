package com.dubaiculture.data.repository.login.remote.response.changepassword

import com.dubaiculture.data.repository.login.remote.response.UserDTO
import com.google.gson.annotations.SerializedName

data class ChangedPasswordResponseDTO(
    @SerializedName(value = "Message")
    val message: String
)