package com.app.dubaiculture.data.repository.survey.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SurveyFormResponse(
    @SerializedName("Result")
    @Expose
    val result: Result
) : BaseResponse() {}