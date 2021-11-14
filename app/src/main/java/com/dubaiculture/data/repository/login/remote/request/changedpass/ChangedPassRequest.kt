package com.dubaiculture.data.repository.login.remote.request.changedpass

data class ChangedPassRequest(
    var oldPass : String,
    var newPass : String,
    var confirmPass : String
)