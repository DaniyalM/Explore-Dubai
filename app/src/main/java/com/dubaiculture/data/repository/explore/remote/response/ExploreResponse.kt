package com.dubaiculture.data.repository.explore.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.explore.local.models.Result
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ExploreResponse constructor(
    @SerializedName("Result")
    @Expose
    val Result: Result
) : BaseResponse()
