package com.app.dubaiculture.data.repository.forgot

import com.app.dubaiculture.data.repository.forgot.remote.request.ForgotRequest
import com.app.dubaiculture.data.repository.forgot.remote.response.ForgotResponse
import com.app.dubaiculture.data.repository.forgot.remote.response.ForgotResponseDTO
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgotRepository {
    @POST("Auth/ForgotPassword")
    suspend fun forgot(@Body forgotResponseDTO: ForgotResponseDTO): ForgotResponse
}