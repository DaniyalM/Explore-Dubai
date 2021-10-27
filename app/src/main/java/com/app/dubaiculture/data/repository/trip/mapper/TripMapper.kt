package com.app.dubaiculture.data.repository.trip.mapper

import com.app.dubaiculture.data.repository.trip.local.UserTypes
import com.app.dubaiculture.data.repository.trip.local.UsersType
import com.app.dubaiculture.data.repository.trip.remote.response.UserTypeResponse
import com.app.dubaiculture.data.repository.trip.remote.response.UserTypeResponseDTO

fun transform(userTypeResponse: UserTypeResponse): UserTypeResponseDTO {
    return UserTypeResponseDTO(
        Title = userTypeResponse.userTypeResponseDTO.Title,
        UsersType = userTypeResponse.userTypeResponseDTO.UsersType
    )
}

fun transformUserType(userTypeResponse: UserTypeResponseDTO) = UserTypes(
    title = userTypeResponse.Title,
    usersType = userTypeResponse.UsersType.mapIndexed { index, usersTypeDTO ->
        UsersType(
            id = index + 1,
            image = usersTypeDTO.Image,
            title = usersTypeDTO.Title
        )
    }
)