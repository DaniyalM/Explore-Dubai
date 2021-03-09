package com.app.dubaiculture.data.repository.event.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.app.dubaiculture.data.repository.event.local.models.Result
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HomeEventResponse constructor(
    @SerializedName("Result")
    @Expose
    val Result: Result
) : BaseResponse()