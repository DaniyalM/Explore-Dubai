import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest

import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.login.remote.request.changedpass.ChangedPassRequest
import com.app.dubaiculture.data.repository.login.remote.request.changedpass.ChangedPassRequestDTO


fun transform(loginRequest: LoginRequest): LoginRequestDTO {
    return LoginRequestDTO(
        PhoneNumber = loginRequest.phoneNumber,
        Password = loginRequest.password,
        Email = loginRequest.email
    )
}

fun transformChangedPass(changedPassRequest: ChangedPassRequest): ChangedPassRequestDTO {
    return ChangedPassRequestDTO(
        OldPassword = changedPassRequest.oldPass,
        NewPassword = changedPassRequest.newPass,
        ConfirmPassword = changedPassRequest.confirmPass
    )

}
