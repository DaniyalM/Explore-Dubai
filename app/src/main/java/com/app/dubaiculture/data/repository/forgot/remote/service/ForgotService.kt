package com.app.dubaiculture.data.repository.forgot.remote.service

import com.app.dubaiculture.data.repository.forgot.remote.request.ForgotRequestDTO
import com.app.dubaiculture.data.repository.forgot.remote.response.ForgotResponse
import com.app.dubaiculture.data.repository.forgot.remote.response.ForgotResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgotService {
    @POST("Auth/ForgotPassword")
    suspend fun forgot(@Body forgotRequestDTO: ForgotRequestDTO): ForgotResponse
}