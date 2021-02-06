package com.app.dubaiculture.data.repository.base

import com.google.gson.annotations.SerializedName

open class BaseResponse() {
    @SerializedName(value = "Succeeded")
    val succeeded: Boolean = false

    @SerializedName(value = "StatusCode")
    val statusCode: Int = 0

    @SerializedName(value = "ErrorMessage")
    val errorMessage: String = ""
}