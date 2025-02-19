package com.dubaiculture.data.repository.registeration.remote

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.registeration.remote.request.confirmOTP.ConfirmOTPRequestDTO
import com.dubaiculture.data.repository.registeration.remote.request.resendOTP.ResendOTPRequestDTO
import com.dubaiculture.data.repository.registeration.service.RegistrationService
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequestDTO
import com.dubaiculture.data.repository.registeration.remote.response.register.RegistrationResponse
import com.dubaiculture.data.repository.registeration.remote.response.confirmOTP.ConfirmOTPResponse
import javax.inject.Inject

class RegistrationRDS @Inject constructor(private val registrationService: RegistrationService) :
    BaseRDS() {
    suspend fun registration(registrationRequestDTO: RegistrationRequestDTO): Result<RegistrationResponse> {
        return safeApiCall {
            registrationService.registration(registrationRequestDTO)
        }
    }
        suspend fun confirmOTP(confirmOTPRequestDTO: ConfirmOTPRequestDTO): Result<ConfirmOTPResponse> {
            return safeApiCall {
                registrationService.confirmOTP(confirmOTPRequestDTO)
            }
        }

    suspend fun validateOTP(confirmOTPRequestDTO: ConfirmOTPRequestDTO): Result<ConfirmOTPResponse> {
        return safeApiCall {
            registrationService.validateOTP(confirmOTPRequestDTO)
        }
    }

    suspend fun resendVerificationOTP(resendOTPRequestDTO: ResendOTPRequestDTO): Result<ConfirmOTPResponse> {
        return safeApiCall {
            registrationService.resendVerificationOTP(resendOTPRequestDTO)
        }
    }


}