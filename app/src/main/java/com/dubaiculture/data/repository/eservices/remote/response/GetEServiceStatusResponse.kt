package com.dubaiculture.data.repository.eservices.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.base.EServiceBaseResponse
import com.google.gson.annotations.SerializedName

class GetEServiceStatusResponse constructor(
    @SerializedName(value = "Result") val result: EServiceStatusResult
) : BaseResponse()

class EServiceStatusResult(
    @SerializedName(value = "Services") val services: List<EServiceStatusDto    >
)

class EServiceStatusDto(
    val ID: String,
    val Title: String,
    val CategoryID: String,
    val Category: String,
    val Summary: String,
    val IsFavourite: Boolean,
    val StartServiceText: String,
    val StartServiceUrl: String,
    val FormName: String,
    val FormSubmitURL: String,
    val Request: EServiceStatusDetailsDto
)

class EServiceStatusDetailsDto(
    val ID: String,
    val DateTime: String,
    val Status: Boolean
)