package com.dubaiculture.data.repository.user.mapper

import com.dubaiculture.data.repository.login.remote.response.LoginResponseDTO
import com.dubaiculture.data.repository.login.remote.response.UserDTO
import com.dubaiculture.data.repository.user.local.User


fun transform(userDTO: UserDTO, loginResponseDTO: LoginResponseDTO): User {
    return User(
        userId = userDTO.Id,
        email = userDTO.Email,
        phoneNumber = userDTO.PhoneNumber,
        userName = userDTO.UserName,
        userImage = userDTO.UserImage,
        token = loginResponseDTO.Token,
        expireIn = loginResponseDTO.ExpiresIn,
        refreshToken = loginResponseDTO.RefreshToken,
        verificationToken = userDTO.VerificiationToken?:"",
        hasPassword = userDTO.HasPassword
    )
}
