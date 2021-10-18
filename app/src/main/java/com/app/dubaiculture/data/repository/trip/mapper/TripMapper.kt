package com.app.dubaiculture.data.repository.trip.mapper

import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.trip.remote.response.UserTypeResponse
import com.app.dubaiculture.data.repository.trip.remote.response.UserTypeResponseDTO

fun transform(userTypeResponse: UserTypeResponse): UserTypeResponseDTO {
    return UserTypeResponseDTO(
        Title = userTypeResponse.userTypeResponseDTO.Title,
        UsersType = userTypeResponse.userTypeResponseDTO.UsersType
    )
}