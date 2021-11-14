package com.dubaiculture.data.repository.attraction.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.attraction.remote.Result
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AttractionResponse constructor(
    @SerializedName("Result")
    @Expose
    val Result: Result
) : BaseResponse()