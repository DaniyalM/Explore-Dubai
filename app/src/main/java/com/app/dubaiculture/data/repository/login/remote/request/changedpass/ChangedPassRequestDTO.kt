package com.app.dubaiculture.data.repository.login.remote.request.changedpass

data class ChangedPassRequestDTO(
    var OldPassword : String,
    var NewPassword : String,
    var ConfirmPassword : String
)