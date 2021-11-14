package com.app.neomads.data.repository.registration.remote.request.register


class RegistrationRequest(

    val email: String,

    val password: String,

    val confirmPassword: String,

    val fullName: String,

    val phoneNumber: String,
    val culture: String = "en"
)