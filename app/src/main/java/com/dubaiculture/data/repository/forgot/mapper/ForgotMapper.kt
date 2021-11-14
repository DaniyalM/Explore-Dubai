package com.dubaiculture.data.repository.forgot.mapper

import com.dubaiculture.data.repository.forgot.remote.request.ForgotRequest
import com.dubaiculture.data.repository.forgot.remote.request.ForgotRequestDTO

fun transform(forgotRequest: ForgotRequest): ForgotRequestDTO {
    return ForgotRequestDTO(
        Email = forgotRequest.email
    )

}