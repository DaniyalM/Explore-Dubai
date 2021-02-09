package com.app.dubaiculture.data.repository.forgot.mapper

import com.app.dubaiculture.data.repository.forgot.remote.request.ForgotRequest
import com.app.dubaiculture.data.repository.forgot.remote.request.ForgotRequestDTO

fun transform(forgotRequest: ForgotRequest): ForgotRequestDTO {
    return ForgotRequestDTO(
        Email = forgotRequest.email
    )

}