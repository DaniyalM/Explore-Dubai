package com.app.dubaiculture.data.repository.trip.remote.response

data class UserTypeResponseDTO(
    val Title: String,
    val UsersType: List<UsersType>
)

data class UsersType(
    val Image: String,
    val Title: String
)