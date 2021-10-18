package com.app.dubaiculture.data.repository.login.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.login.remote.request.UaeLoginRequestDTO
import com.app.dubaiculture.data.repository.login.remote.request.changedpass.ChangedPassRequestDTO
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponse
import com.app.dubaiculture.data.repository.login.remote.response.changepassword.ChangedPasswordResponse
import com.app.dubaiculture.data.repository.login.remote.response.resendverification.ResendVerificationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface  LoginService :BaseService{
    @POST("Auth/MobileLogin")
    suspend fun login(@Body loginRequestDTO: LoginRequestDTO): LoginResponse


    @POST("Auth/UAEPassLogin")
    suspend fun loginWithUae(@Body uaeLoginRequestDTO: UaeLoginRequestDTO): LoginResponse

    // should have only one service for login with the help of email or phone. but here is two different service dont know why.
    @POST("Auth/Login")
    suspend fun loginWithEmail(@Body loginRequestDTO: LoginRequestDTO): LoginResponse


    @POST("Auth/ResendVerification")
    suspend fun resendVerification(@Body loginRequestDTO: LoginRequestDTO): ResendVerificationResponse


    @POST("Auth/ChangePassword")
    suspend fun changedPassword(@Body changedPassRequestDTO: ChangedPassRequestDTO): ChangedPasswordResponse
}