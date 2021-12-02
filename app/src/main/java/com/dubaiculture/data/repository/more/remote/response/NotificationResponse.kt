package com.dubaiculture.data.repository.more.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NotificationResponse constructor(
    @SerializedName("Result")
    @Expose
    val Result: NotificationResult
) : BaseResponse()