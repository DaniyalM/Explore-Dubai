package com.app.dubaiculture.data.repository.login.remote.response

import com.google.gson.annotations.SerializedName

open class UserDTO (
    @SerializedName(value = "Email")
    val Email: String,
    @SerializedName(value = "Id")
    val Id: String,
    @SerializedName(value = "PhoneNumber")
    val PhoneNumber: String,
    @SerializedName(value = "UserName")
    val UserName: String,
)