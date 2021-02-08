import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO



fun transform(loginRequest: LoginRequest): LoginRequestDTO {
    return LoginRequestDTO(
        Email = loginRequest.email,
        Password = loginRequest.password
    )

}

