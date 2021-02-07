package com.app.dubaiculture.data.repository.login.remote.response

 class LoginResponseDTO
{
    data class Result(
        val ExpiresIn: Int,
        val RefreshToken: String,
        val Token: String,
        val user: User
    ) {
        data class User(
            val Email: String,
            val Id: String,
            val PhoneNumber: String,
            val UserName: String
        )
    }
}