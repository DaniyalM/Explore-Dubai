package com.app.dubaiculture.data.repository.base

import com.app.dubaiculture.data.repository.explore.local.models.Result
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse() {
    @SerializedName(value = "Succeeded")
    val succeeded: Boolean = false

    @SerializedName(value = "StatusCode")
    val statusCode: Int = 0

    @SerializedName(value = "ErrorMessage")
    val errorMessage: String = ""


}