package com.app.dubaiculture.data.repository.trip.local

data class UserTypes(
    val title: String,
    val usersType: List<UsersType>
)

data class UsersType(
    val id: Int?,
    val image: String,
    val title: String
)
