package com.app.dubaiculture.data.repository.registeration.remote

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.registeration.remote.request.confirmOTP.ConfirmOTPRequestDTO
import com.app.dubaiculture.data.repository.registeration.remote.service.RegistrationService
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequestDTO
import com.app.dubaiculture.data.repository.registeration.remote.response.register.RegistrationResponse
import com.app.dubaiculture.data.repository.registeration.remote.response.confirmOTP.ConfirmOTPResponse
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
}