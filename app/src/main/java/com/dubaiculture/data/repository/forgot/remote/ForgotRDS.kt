package com.dubaiculture.data.repository.forgot.remote

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.forgot.remote.request.ForgotRequestDTO
import com.dubaiculture.data.repository.forgot.remote.response.ForgotResponse
import com.dubaiculture.data.repository.forgot.service.ForgotService
import javax.inject.Inject

class ForgotRDS  @Inject constructor(private val forgotService: ForgotService) : BaseRDS() {
    suspend fun forgotEmail(forgotRequestDTO: ForgotRequestDTO): Result<ForgotResponse> {
        return safeApiCall {
            forgotService.forgot(forgotRequestDTO)
        }
    }
}