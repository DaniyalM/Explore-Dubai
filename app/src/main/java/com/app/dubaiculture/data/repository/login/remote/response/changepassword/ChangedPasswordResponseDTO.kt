package com.app.dubaiculture.data.repository.login.remote.response.changepassword

import com.app.dubaiculture.data.repository.login.remote.response.UserDTO
import com.google.gson.annotations.SerializedName

data class ChangedPasswordResponseDTO(
    @SerializedName(value = "User")
    val userDTO: UserDTO,
    @SerializedName(value = "ExpiresIn")
    val ExpiresIn: Int,
)