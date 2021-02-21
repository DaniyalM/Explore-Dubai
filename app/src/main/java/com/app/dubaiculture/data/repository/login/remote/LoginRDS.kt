package com.app.dubaiculture.data.repository.login.remote

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponse
import com.app.dubaiculture.data.repository.login.remote.response.resendverification.ResendVerificationResponse
import com.app.dubaiculture.data.repository.login.service.LoginService
import javax.inject.Inject

class LoginRDS @Inject constructor(private val loginService: LoginService) : BaseRDS() {

    suspend fun login(loginRequestDTO: LoginRequestDTO): Result<LoginResponse>{
        return safeApiCall {
            loginService.login(loginRequestDTO)
        }
    }

    suspend fun loginWithEmail(loginRequestDTO: LoginRequestDTO): Result<LoginResponse>{
        return safeApiCall {
            loginService.loginWithEmail(loginRequestDTO)
        }
    }

    suspend fun resendVerification(loginRequestDTO: LoginRequestDTO): Result<ResendVerificationResponse>{
        return safeApiCall {
            loginService.resendVerification(loginRequestDTO)
        }
    }



}