package com.app.dubaiculture.utils

import android.text.TextUtils
import com.app.dubaiculture.data.repository.user.UserRepository
import com.app.dubaiculture.data.repository.user.local.guest.Guest
import dagger.Lazy
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionManager @Inject constructor(private val userRepository: Lazy<UserRepository>) {
    //    var authorizationToken: String = "auth_token1234567890987654321"

    suspend fun getGuestToken(deviceId: String): Guest? {
        val userRepo = userRepository.get()
        var guest = userRepo.getLastGuestUser()

//        "2c0455379d5ec951"
        if (guest == null) {
            guest = userRepo.guestToken(deviceId)
        } else {
            if (!TextUtils.isEmpty(guest.token)) {

                val timeDifference = System.currentTimeMillis() - guest.createdAt
                val expireDifference = guest.ExpiresIn!! - 4800
                val expiry_time = TimeUnit.SECONDS.toMillis(expireDifference.toLong())

                if (timeDifference >= expiry_time) {
                    guest = userRepo.guestToken(deviceId)
                }

            }
        }
        return guest
    }

    suspend fun getToken(): Pair<Boolean, String> {
        val userRepo = userRepository.get()
        var user = userRepo.getLastUser()
        if (user == null) {
            return Pair(false, "")
        } else {
            val token = user.refreshToken
            //if token is expired
            if (!TextUtils.isEmpty(token)) {
                //Finding Difference to Time when token created
                val timeDifference = System.currentTimeMillis() - user.createdAt
                //For the save side reducing some amount from expiry
                val expireDifference = user.expireIn!! - 500
                //Final Expiry Time
                val expiryTime = TimeUnit.SECONDS.toMillis(expireDifference.toLong())
                if (timeDifference >= expiryTime) {
                    user = userRepo.refreshToken(user.token, user.refreshToken)
                }
            }

            // check for null as db query might fail
            return if (user != null) {
                Pair(true, user.token)
            } else {
                Pair(false, "")
            }

        }
    }
}