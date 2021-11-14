package com.dubaiculture.data.repository.news.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsResponse constructor(
        @SerializedName("Result")
        @Expose
        val Result: Result
) : BaseResponse()