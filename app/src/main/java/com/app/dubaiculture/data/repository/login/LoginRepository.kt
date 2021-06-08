package com.app.dubaiculture.data.repository.login

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.explore.mapper.transformExplore
import com.app.dubaiculture.data.repository.login.remote.LoginRDS
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest
import com.app.dubaiculture.data.repository.login.remote.request.changedpass.ChangedPassRequest
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponse
import com.app.dubaiculture.data.repository.login.remote.response.changepassword.ChangedPasswordResponse
import com.app.dubaiculture.data.repository.login.remote.response.resendverification.ResendVerificationResponse
import transform
import transformChangedPass
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginRDS: LoginRDS):
    BaseRepository() {

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

    suspend fun changedPassword(changedPassRequest: ChangedPassRequest): Result<ChangedPasswordResponse> {
        return when(val resultRDS = loginRDS.changedPassword(transformChangedPass(changedPassRequest))){
            is Result.Success ->{
                val listRDS = resultRDS
                if (listRDS.value.statusCode != 200) {
                    Result.Failure(true,listRDS.value.statusCode,null,listRDS.value.errorMessage)
                }else{
                    Result.Success(listRDS.value)
                }
            }
            is Result.Failure-> resultRDS
            is Result.Error -> resultRDS
        }
    }
}