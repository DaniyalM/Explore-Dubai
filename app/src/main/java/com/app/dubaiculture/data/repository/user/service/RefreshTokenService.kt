package com.app.dubaiculture.data.repository.user.service

import com.app.dubaiculture.data.repository.user.remote.request.GuestTokenRequestDTO
import com.app.dubaiculture.data.repository.user.remote.request.RefreshTokenRequestDTO
import com.app.dubaiculture.data.repository.user.remote.response.GuestTokenResponse
import com.app.dubaiculture.data.repository.user.remote.response.RefreshTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface  RefreshTokenService {
    @POST("Auth/RefreshToken")
    suspend fun refreshToken(@Body refreshTokenRequestDTO: RefreshTokenRequestDTO): RefreshTokenResponse

    @POST("api/Auth/GuestLogin")
    suspend fun guestToken(@Body guestTokenRequestDTO: GuestTokenRequestDTO):GuestTokenResponse

}