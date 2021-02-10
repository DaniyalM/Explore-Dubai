package com.app.dubaiculture.data.repository.user.remote

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.user.remote.request.RefreshTokenRequestDTO
import com.app.dubaiculture.data.repository.user.remote.response.RefreshTokenResponse
import com.app.dubaiculture.data.repository.user.remote.service.RefreshTokenService
import javax.inject.Inject

class UserRDS @Inject constructor(private val refreshTokenService: RefreshTokenService) :
    BaseRDS() {
    suspend fun refreshToken(refreshTokenRequestDTO: RefreshTokenRequestDTO): Result<RefreshTokenResponse> {
        return safeApiCall {
            refreshTokenService.refreshToken(refreshTokenRequestDTO)
        }
    }
}