package com.app.dubaiculture.data.repository.setpassword.remote.request

data class SetPasswordRequestDTO( val VerificationCode : String , val NewPassword : String , val ConfirmPassword : String )