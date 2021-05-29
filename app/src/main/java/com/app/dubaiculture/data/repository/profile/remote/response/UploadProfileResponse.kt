package com.app.dubaiculture.data.repository.profile.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class UploadProfileResponse constructor(
        @SerializedName(value = "Value")
        val value: String
) : BaseResponse()