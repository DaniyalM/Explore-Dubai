package com.app.dubaiculture.data.repository.login.remote.service

import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface  LoginService {
    @POST("Auth/Login")
    suspend fun login(@Body loginRequestDTO: LoginRequestDTO): LoginResponse
}