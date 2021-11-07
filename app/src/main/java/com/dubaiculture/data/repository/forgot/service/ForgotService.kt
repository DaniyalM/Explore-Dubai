package com.dubaiculture.data.repository.forgot.service

import com.dubaiculture.data.repository.base.BaseService
import com.dubaiculture.data.repository.forgot.remote.request.ForgotRequestDTO
import com.dubaiculture.data.repository.forgot.remote.response.ForgotResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgotService :BaseService{
    @POST("Auth/ForgotPassword")
    suspend fun forgot(@Body forgotRequestDTO: ForgotRequestDTO): ForgotResponse
}