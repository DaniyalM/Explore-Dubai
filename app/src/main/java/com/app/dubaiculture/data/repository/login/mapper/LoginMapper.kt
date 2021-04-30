import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest

import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO



fun transform(loginRequest: LoginRequest): LoginRequestDTO {
    return LoginRequestDTO(
        PhoneNumber = loginRequest.phoneNumber,
        Password = loginRequest.password,
        Email = loginRequest.email
    )

}

