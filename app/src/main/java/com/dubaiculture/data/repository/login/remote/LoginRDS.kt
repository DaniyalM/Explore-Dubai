package com.dubaiculture.data.repository.login.remote

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.dubaiculture.data.repository.login.remote.request.UaeLoginRequestDTO
import com.dubaiculture.data.repository.login.remote.request.changedpass.ChangedPassRequestDTO
import com.dubaiculture.data.repository.login.remote.response.LoginResponse
import com.dubaiculture.data.repository.login.remote.response.changepassword.ChangedPasswordResponse
import com.dubaiculture.data.repository.login.remote.response.resendverification.ResendVerificationResponse
import com.dubaiculture.data.repository.login.service.LoginService
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

    suspend fun loginWithUaePass(uaeLoginRequestDTO: UaeLoginRequestDTO): Result<LoginResponse>{
        return safeApiCall {
            loginService.loginWithUae(uaeLoginRequestDTO)
        }
    }

    suspend fun resendVerification(loginRequestDTO: LoginRequestDTO): Result<ResendVerificationResponse>{
        return safeApiCall {
            loginService.resendVerification(loginRequestDTO)
        }
    }

    suspend fun changedPassword(changedPassRequestDTO: ChangedPassRequestDTO): Result<ChangedPasswordResponse>{
        return safeApiCall {
            loginService.changedPassword(changedPassRequestDTO)
        }
    }

}