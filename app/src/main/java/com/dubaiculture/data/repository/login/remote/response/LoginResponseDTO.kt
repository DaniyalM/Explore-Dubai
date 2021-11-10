package com.dubaiculture.data.repository.login.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponseDTO constructor(
    @SerializedName(value = "User")
    val userDTO: UserDTO,
    @SerializedName(value = "UAEPass")
    val userUaePass: UAEPassDTO,
    @SerializedName(value = "ExpiresIn")
    val ExpiresIn: Int,
    @SerializedName(value = "RefreshToken")
    val RefreshToken: String,
    @SerializedName(value = "Token")
    val Token: String,
    @SerializedName(value = "IsLinked")
    val IsLinked: Boolean,
    @SerializedName(value = "UpdateMessage")
    val UpdateMessage: String,

)









