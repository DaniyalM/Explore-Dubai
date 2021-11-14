package com.dubaiculture.data.repository.survey.remote.response

import com.dubaiculture.data.repository.survey.request.FormDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    val Added : String,
    @SerializedName("Form")
    @Expose
    val Form : FormDTO)