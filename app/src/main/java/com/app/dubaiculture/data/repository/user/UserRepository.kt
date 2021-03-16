package com.app.dubaiculture.data.repository.user

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponseDTO
import com.app.dubaiculture.data.repository.login.remote.response.UserDTO
import com.app.dubaiculture.data.repository.user.local.User
import com.app.dubaiculture.data.repository.user.local.UserLDS
import com.app.dubaiculture.data.repository.user.local.guest.Guest
import com.app.dubaiculture.data.repository.user.local.guest.GuestLDS
import com.app.dubaiculture.data.repository.user.mapper.transform
import com.app.dubaiculture.data.repository.user.remote.UserRDS
import com.app.dubaiculture.data.repository.user.remote.request.GuestTokenRequestDTO
import com.app.dubaiculture.data.repository.user.remote.request.RefreshTokenRequestDTO
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRDS: UserRDS,
    private val userLDS: UserLDS,
    private val guestLDS: GuestLDS
) : BaseRepository<UserRDS>(userRDS) {


    suspend fun saveUser(userDTO: UserDTO, loginResponseDTO: LoginResponseDTO) {
        val user = transform(userDTO, loginResponseDTO)
        userLDS.insert(
            user
        )
    }
    suspend fun getLastUser(): User? = userLDS.getUser()
    suspend fun getLastGuestUser():Guest?=guestLDS.getGuestUser()

    suspend fun refreshToken(token: String, refreshToken: String): User? {
        Timber.e("Token Refresh")
        when (val resultRDS = userRDS.refreshToken(RefreshTokenRequestDTO(Token = token,RefreshToken =  refreshToken))) {
            is Result.Success -> {
                if (resultRDS.value.statusCode!=200){
                    Result.Failure(resultRDS.value.succeeded,resultRDS.value.statusCode,null)
                }else{
                    val user = userLDS.getUser()
                    Timber.e("Request Perform ${user?.token}")
                    user?.apply {
                        val resp = resultRDS.value.refreshTokenResponseDTO
                        this.refreshToken = resp.refreshToken
                        this.token = resp.token
                        userLDS.update(this)
                        return userLDS.getUser()
                    }
                }

            }

        }
        return null
    }

    suspend fun guestToken(deviceId:String): Guest? {
        when(val resultRDS=userRDS.getGuestToken(GuestTokenRequestDTO(deviceId))){
            is Result.Success -> {
                val resp = resultRDS.value.guestTokenResponseDTO
                val guest = guestLDS.getGuestUser()
                guest?.apply {
                    guestLDS.delete(this)
                }
                guestLDS.insert(Guest(token=resp.token,ExpiresIn = resp.expireIn))

                return guestLDS.getGuestUser()

            }
        }
        return null

    }
}
