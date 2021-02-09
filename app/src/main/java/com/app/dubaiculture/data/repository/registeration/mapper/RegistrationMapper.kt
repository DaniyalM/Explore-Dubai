package com.app.dubaiculture.data.repository.registeration.remote.mapper

import com.app.dubaiculture.data.repository.registeration.remote.request.confirmOTP.ConfirmOTPRequest
import com.app.dubaiculture.data.repository.registeration.remote.request.confirmOTP.ConfirmOTPRequestDTO
import com.app.dubaiculture.data.repository.registeration.remote.request.resendOTP.ResendOTPRequest
import com.app.dubaiculture.data.repository.registeration.remote.request.resendOTP.ResendOTPRequestDTO
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequest
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequestDTO

fun transform(registrationRequest: RegistrationRequest): RegistrationRequestDTO {

    return RegistrationRequestDTO(
        Email = registrationRequest.email,
        Password = registrationRequest.password,
        ConfirmPassword = registrationRequest.confirmPassword,
        FullName = registrationRequest.fullName,
        PhoneNumber = registrationRequest.phoneNumber,
    )


}

fun transform(confirmOTPRequest: ConfirmOTPRequest): ConfirmOTPRequestDTO {

    return ConfirmOTPRequestDTO(
        VerificationCode = confirmOTPRequest.verificationCode,
        OTP = confirmOTPRequest.otp,
    )
}



fun transform(resendOTPRequest: ResendOTPRequest): ResendOTPRequestDTO {
    return ResendOTPRequestDTO(
        VerificationCode = resendOTPRequest.verificationCode
    )
}