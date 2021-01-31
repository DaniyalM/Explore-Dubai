package com.app.dubaiculture.data.repository.registeration.remote.mapper

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



