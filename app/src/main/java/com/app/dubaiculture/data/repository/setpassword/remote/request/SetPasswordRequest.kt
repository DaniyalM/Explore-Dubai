package com.app.dubaiculture.data.repository.setpassword.remote.request

data class SetPasswordRequest(val verificationToken : String , val newPassword : String , val confirmPassword : String)
