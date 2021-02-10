package com.app.dubaiculture.data.repository.registeration

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.registeration.remote.RegistrationRDS
import com.app.dubaiculture.data.repository.registeration.remote.mapper.transform
import com.app.dubaiculture.data.repository.registeration.remote.request.confirmOTP.ConfirmOTPRequest
import com.app.dubaiculture.data.repository.registeration.remote.request.resendOTP.ResendOTPRequest
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequest
import com.app.dubaiculture.data.repository.registeration.remote.response.register.RegistrationResponse
import com.app.dubaiculture.data.repository.registeration.remote.response.confirmOTP.ConfirmOTPResponse
import javax.inject.Inject

class RegistrationRepository @Inject constructor(private val registrationRDS: RegistrationRDS) :
    BaseRepository() {
    suspend fun register(registrationRequest: RegistrationRequest): Result<RegistrationResponse> {

        return when (val resultRDS = registrationRDS.registration(transform(registrationRequest))) {
            is Result.Success -> {
                Result.Success(resultRDS.value)
            }
            is Result.Error -> {
                Result.Error(resultRDS.exception)
            }
            is Result.Failure -> {
                Result.Failure(resultRDS.isNetWorkError, resultRDS.errorCode, resultRDS.errorBody)
            }
        }
    }

    suspend fun validateOTP(confirmOTPRequest:ConfirmOTPRequest):Result<ConfirmOTPResponse>{
        return when (val resultRDS = registrationRDS.confirmOTP(transform(confirmOTPRequest))) {
            is Result.Failure -> {
                Result.Failure(resultRDS.isNetWorkError, resultRDS.errorCode, resultRDS.errorBody)
            }
            is Result.Success -> {
                Result.Success(resultRDS.value)
            }
            is Result.Error -> {
                Result.Error(resultRDS.exception)
            }
        }
    }


    suspend fun resendVerificationOTP(resendOTPRequest: ResendOTPRequest):Result<ConfirmOTPResponse>{
        return when (val resultRDS = registrationRDS.resendVerificationOTP(transform(resendOTPRequest))) {
            is Result.Failure -> {
                Result.Failure(resultRDS.isNetWorkError, resultRDS.errorCode, resultRDS.errorBody)
            }
            is Result.Success -> {
                Result.Success(resultRDS.value)
            }
            is Result.Error -> {
                Result.Error(resultRDS.exception)
            }
        }
    }
}