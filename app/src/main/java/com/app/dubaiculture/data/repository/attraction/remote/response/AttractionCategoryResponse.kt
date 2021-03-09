package com.app.dubaiculture.data.repository.attraction.remote.response

import com.app.dubaiculture.data.repository.attraction.local.models.Result
import com.app.dubaiculture.data.repository.base.BaseResponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AttractionCategoryResponse constructor(
    @SerializedName("Result")
    @Expose
    val Result: Result
) : BaseResponse()