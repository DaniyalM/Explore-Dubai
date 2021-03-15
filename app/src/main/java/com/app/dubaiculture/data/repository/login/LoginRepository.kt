package com.app.dubaiculture.data.repository.login

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.login.remote.LoginRDS
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponse
import com.app.dubaiculture.data.repository.login.remote.response.resendverification.ResendVerificationResponse
import transform
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginRDS: LoginRDS):BaseRepository<LoginRDS>(loginRDS) {

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {

            return when(val resultRDS = loginRDS.login(transform(loginRequest))){
                    is Result.Success->{
                        Result.Success(resultRDS.value)
                    }
                    is Result.Error->resultRDS
                    is Result.Failure ->resultRDS
            }
        }

    suspend fun loginWithEmail(loginRequest: LoginRequest): Result<LoginResponse> {
        return when(val resultRDS = loginRDS.loginWithEmail(transform(loginRequest))){
            is Result.Success->{
                Result.Success(resultRDS.value)
            }
            is Result.Error->resultRDS
            is Result.Failure ->resultRDS
        }
    }

    suspend fun resendVerification(loginRequest: LoginRequest): Result<ResendVerificationResponse> {
        return when(val resultRDS = loginRDS.resendVerification(transform(loginRequest))){
            is Result.Success->{
                Result.Success(resultRDS.value)
            }
            is Result.Error->resultRDS
            is Result.Failure ->resultRDS
        }
    }
}