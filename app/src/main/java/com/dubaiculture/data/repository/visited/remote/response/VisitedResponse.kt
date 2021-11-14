package com.dubaiculture.data.repository.visited.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class VisitedResponse(
    @SerializedName(value = "Result")
    val result: MessageDTO
):BaseResponse()
