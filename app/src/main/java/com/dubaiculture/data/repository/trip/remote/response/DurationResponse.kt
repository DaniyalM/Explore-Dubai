package com.dubaiculture.data.repository.trip.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class DurationResponse constructor(@SerializedName(value = "Result") val durationResponseDTO: DurationResponseDTO) :
    BaseResponse()