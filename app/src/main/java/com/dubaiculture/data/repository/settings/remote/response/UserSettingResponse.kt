package com.dubaiculture.data.repository.settings.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.settings.remote.Result
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserSettingResponse(
        @SerializedName("Result")
        @Expose
        val result: Result
):BaseResponse()
