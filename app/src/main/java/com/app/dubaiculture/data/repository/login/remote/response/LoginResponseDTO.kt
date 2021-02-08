package com.app.dubaiculture.data.repository.login.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponseDTO constructor(
    @SerializedName(value = "User")
    val userDTO: UserDTO,
    @SerializedName(value = "ExpiresIn")
    val ExpiresIn: Int,
    @SerializedName(value = "RefreshToken")
    val RefreshToken: String,
    @SerializedName(value = "Token")
    val Token: String
) {

    open inner class UserDTO (
        @SerializedName(value = "Email")
        val Email: String? = null,
        @SerializedName(value = "Id")
        val Id: String? = null,
        @SerializedName(value = "PhoneNumber")
        val PhoneNumber: String? = null,
        @SerializedName(value = "UserName")
        val UserName: String?= null
    )
}










