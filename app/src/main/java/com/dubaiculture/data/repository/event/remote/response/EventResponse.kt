package com.dubaiculture.data.repository.event.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.event.local.models.Result
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EventResponse constructor(@SerializedName("Result")
                                     @Expose val Result: Result
) : BaseResponse()