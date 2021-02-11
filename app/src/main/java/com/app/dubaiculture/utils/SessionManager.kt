package com.app.dubaiculture.utils

import com.app.dubaiculture.data.repository.user.UserRepository
import dagger.Lazy
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionManager @Inject constructor(private val userRepository: Lazy<UserRepository>) {
//    var authorizationToken: String = "auth_token1234567890987654321"

    suspend fun getGuestToken(): Pair<Boolean, String>?{
        val userRepo=userRepository.get()
        return userRepo.guestToken("667bfd7b08bb71d2")
    }

    suspend fun getToken(): Pair<Boolean, String> {
        val userRepo = userRepository.get()
        var user = userRepo.getLastUser()
        if (user == null) {
            return Pair(false, "")
        } else {
//            var token = user.refreshToken
//            var isWantTokenRefresh=false
//            val current_time: Long = Calendar.getInstance().getTimeInMillis()
//            Timber.e("Current Time ${current_time}")
//            user.expireIn?.let {
//                var expiry_time=  TimeUnit.SECONDS.toMillis(it.toLong());
////                val final_time
//            }

            //if token is expired
            if (true) {
                user = userRepo.refreshToken(user.token, user.refreshToken)
            }
            // check for null as db query might fail
            return if (user != null) {
                Pair(true, user.token!!)
            } else {
                Pair(false, "")
            }

        }
    }
}