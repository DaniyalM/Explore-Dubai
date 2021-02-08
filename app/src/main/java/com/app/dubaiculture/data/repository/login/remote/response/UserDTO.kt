package com.app.dubaiculture.data.repository.login.remote.response

import com.google.gson.annotations.SerializedName

open class UserDTO (
    @SerializedName(value = "Email")
    val Email: String? = null,
    @SerializedName(value = "Id")
    val Id: String? = null,
    @SerializedName(value = "PhoneNumber")
    val PhoneNumber: String? = null,
    @SerializedName(value = "UserName")
    val UserName: String?= null
)