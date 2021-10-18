package com.app.dubaiculture.data.repository.popular_service.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.app.dubaiculture.data.repository.popular_service.local.models.Result

data class ServiceResponse constructor(
    val Result: Result
) : BaseResponse()