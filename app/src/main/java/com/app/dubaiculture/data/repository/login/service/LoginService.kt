package com.app.dubaiculture.data.repository.login.service

import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface  LoginService {
    @POST("Auth/MobileLogin")
    suspend fun login(@Body loginRequestDTO: LoginRequestDTO): LoginResponse

    // should have only one service for login with the help of email or phone. but here is two different service dont know why.
    @POST("Auth/Login")
    suspend fun loginWithEmail(@Body loginRequestDTO: LoginRequestDTO): LoginResponse
}