package com.app.dubaiculture.data.repository.setpassword

import com.app.dubaiculture.data.repository.setpassword.remote.request.SetPasswordRequest
import com.app.dubaiculture.data.repository.setpassword.remote.request.SetPasswordRequestDTO

fun transform(setPasswordRequest: SetPasswordRequest) : SetPasswordRequestDTO{

    return SetPasswordRequestDTO(
        VerificationCode = setPasswordRequest.verificationToken,
        NewPassword = setPasswordRequest.newPassword,
        ConfirmPassword = setPasswordRequest.confirmPassword
    )
}