package com.app.dubaiculture.data.repository.forgot.remote

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.forgot.remote.request.ForgotRequestDTO
import com.app.dubaiculture.data.repository.forgot.remote.response.ForgotResponse
import com.app.dubaiculture.data.repository.forgot.service.ForgotService
import javax.inject.Inject

class ForgotRDS  @Inject constructor(private val forgotService: ForgotService) : BaseRDS() {
    suspend fun forgotEmail(forgotRequestDTO: ForgotRequestDTO): Result<ForgotResponse> {
        return safeApiCall {
            forgotService.forgot(forgotRequestDTO)
        }
    }
}