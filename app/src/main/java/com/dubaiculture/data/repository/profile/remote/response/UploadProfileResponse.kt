package com.dubaiculture.data.repository.profile.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.profile.remote.Result
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UploadProfileResponse constructor(
        @SerializedName("Result")
        @Expose
        val result: Result
) : BaseResponse()