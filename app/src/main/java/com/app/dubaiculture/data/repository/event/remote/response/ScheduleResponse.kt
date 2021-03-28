package com.app.dubaiculture.data.repository.event.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class ScheduleResponse constructor(@SerializedName(value = "Result") val scheduleResponseDTO: ScheduleResponseDTO) :
    BaseResponse() {
}