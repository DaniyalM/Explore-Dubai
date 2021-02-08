package com.app.dubaiculture.data.repository.registeration.mapper

import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponseDTO
import com.app.dubaiculture.data.repository.registeration.remote.request.confirmOTP.ConfirmOTPRequest
import com.app.dubaiculture.data.repository.registeration.remote.request.confirmOTP.ConfirmOTPRequestDTO
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequest
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequestDTO

fun transform(loginRequest: LoginRequest): LoginRequestDTO {
    return LoginRequestDTO(
        Email = loginRequest.email,
        Password = loginRequest.password
    )
}



