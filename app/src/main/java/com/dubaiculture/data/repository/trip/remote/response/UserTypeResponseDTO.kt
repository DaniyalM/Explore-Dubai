package com.dubaiculture.data.repository.trip.remote.response

data class UserTypeResponseDTO(
    val Title: String,
    val UsersType: List<UsersTypeDTO>
)

data class UsersTypeDTO(
    val Image: String,
    val Title: String
)