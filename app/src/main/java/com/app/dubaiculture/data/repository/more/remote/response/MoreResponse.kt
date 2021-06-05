package com.app.dubaiculture.data.repository.more.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.app.dubaiculture.data.repository.more.remote.response.Result
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoreResponse constructor(
    @SerializedName("Result")
    @Expose
    val Result: Result
) : BaseResponse()