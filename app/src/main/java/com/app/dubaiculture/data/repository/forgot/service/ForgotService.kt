package com.app.dubaiculture.data.repository.forgot.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.forgot.remote.request.ForgotRequestDTO
import com.app.dubaiculture.data.repository.forgot.remote.response.ForgotResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgotService :BaseService{
    @POST("Auth/ForgotPassword")
    suspend fun forgot(@Body forgotRequestDTO: ForgotRequestDTO): ForgotResponse
}