package com.dubaiculture.data.repository.login

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRepository
import com.dubaiculture.data.repository.login.local.UAEPass
import com.dubaiculture.data.repository.login.remote.LoginRDS
import com.dubaiculture.data.repository.login.remote.request.LoginRequest
import com.dubaiculture.data.repository.login.remote.request.UAELoginRequest
import com.dubaiculture.data.repository.login.remote.request.changedpass.ChangedPassRequest
import com.dubaiculture.data.repository.login.remote.response.LoginResponse
import com.dubaiculture.data.repository.login.remote.response.changepassword.ChangedPasswordResponse
import com.dubaiculture.data.repository.login.remote.response.resendverification.ResendVerificationResponse
import com.dubaiculture.utils.security.rds.SecurityManagerRDS
import transform
import transformChangedPass
import transformUaeRequest
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginRDS: LoginRDS,
    private val securityManager: SecurityManagerRDS
) :
    BaseRepository() {


    suspend fun loginWithUae(uaeLoginRequest: UAELoginRequest):Result<LoginResponse>{
        return when(val resultRds=loginRDS.loginWithUaePass(transformUaeRequest(uaeLoginRequest))){
            is Result.Success -> {
                if (resultRds.value.succeeded){
                    Result.Success(resultRds.value)
                }
                else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Failure -> resultRds
            else -> resultRds
        }
    }

    suspend fun linkWithUae(uaeLoginRequest: UAELoginRequest):Result<LoginResponse>{
        return when(val resultRds=loginRDS.loginWithUaePass(transformUaeRequest(uaeLoginRequest))){
            is Result.Success -> {
                if (resultRds.value.succeeded){
                    Result.Success(resultRds.value)
                }
                else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Failure -> resultRds
            else -> resultRds
        }
    }
    suspend fun linkWithUaeCreateAccount(uaeLoginRequest: UAELoginRequest):Result<LoginResponse>{
        return when(val resultRds=loginRDS.loginWithUaePass(transformUaeRequest(uaeLoginRequest))){
            is Result.Success -> {
                if (resultRds.value.succeeded){
                    Result.Success(resultRds.value)
                }
                else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Failure -> resultRds
            else -> resultRds
        }
    }

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {

        return when (val resultRDS = loginRDS.login(transform(loginRequest))) {
            is Result.Success -> {
                Result.Success(resultRDS.value)
            }

            is Result.Failure -> resultRDS
            else -> resultRDS
        }
    }

    suspend fun loginWithEmail(loginRequest: LoginRequest): Result<LoginResponse> {
        return when (val resultRDS = loginRDS.loginWithEmail(transform(loginRequest))) {
            is Result.Success -> {
                Result.Success(resultRDS.value)
            }
            is Result.Error -> resultRDS
            is Result.Failure -> resultRDS
        }
    }

    suspend fun resendVerification(loginRequest: LoginRequest): Result<ResendVerificationResponse> {
        return when (val resultRDS = loginRDS.resendVerification(transform(loginRequest))) {
            is Result.Success -> {
                Result.Success(resultRDS.value)
            }
            is Result.Error -> resultRDS
            is Result.Failure -> resultRDS
        }
    }

    suspend fun changedPassword(changedPassRequest: ChangedPassRequest): Result<ChangedPasswordResponse> {
        return when (val resultRDS =
            loginRDS.changedPassword(transformChangedPass(changedPassRequest))) {
            is Result.Success -> {
                val listRDS = resultRDS
                if (listRDS.value.statusCode != 200) {
                    Result.Failure(true, listRDS.value.statusCode, null, listRDS.value.errorMessage)
                } else {
                    Result.Success(listRDS.value)
                }
            }
            is Result.Failure -> resultRDS
            is Result.Error -> resultRDS
        }
    }
}