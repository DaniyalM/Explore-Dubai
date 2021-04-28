package com.app.dubaiculture.data.repository.registeration.service

import com.app.dubaiculture.data.repository.registeration.remote.request.confirmOTP.ConfirmOTPRequestDTO
import com.app.dubaiculture.data.repository.registeration.remote.request.resendOTP.ResendOTPRequestDTO
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequestDTO
import com.app.dubaiculture.data.repository.registeration.remote.response.register.RegistrationResponse
import com.app.dubaiculture.data.repository.registeration.remote.response.confirmOTP.ConfirmOTPResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationService {


    //Registration
    @POST("Auth/Register")
    suspend fun registration(@Body registrationRequestDTO: RegistrationRequestDTO): RegistrationResponse


    //Confirm OTP for Registration
    @POST("Auth/ConfirmAccount")
    suspend fun confirmOTP(@Body confirmOTPRequestDTO: ConfirmOTPRequestDTO): ConfirmOTPResponse


    //Validate OTP for Forgot Password
    @POST("Auth/ValidateOTP")
    suspend fun validateOTP(@Body confirmOTPRequestDTO: ConfirmOTPRequestDTO): ConfirmOTPResponse

    //Confirm OTP for Registration
    @POST("Auth/ResendVerification")
    suspend fun resendVerificationOTP(@Body resendOTPRequestDTO: ResendOTPRequestDTO): ConfirmOTPResponse
}