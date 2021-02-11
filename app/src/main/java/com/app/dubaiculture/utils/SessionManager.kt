package com.app.dubaiculture.utils

import android.util.Log
import com.app.dubaiculture.data.repository.user.UserRepository
import javax.inject.Inject
import javax.inject.Singleton
import dagger.Lazy


@Singleton
class SessionManager @Inject constructor(private val userRepository: Lazy<UserRepository>) {
//    var authorizationToken: String = "auth_token1234567890987654321"

    suspend fun getGuestToken(): Pair<Boolean,String>?{
        val userRepo=userRepository.get()
        return userRepo.guestToken("667bfd7b08bb71d2")
    }

    suspend fun getToken(): Pair<Boolean, String> {
        val userRepo = userRepository.get()
        var user = userRepo.getLastUser()
        if (user == null) {
            return Pair(false, "")
        } else {
            var token = user.refreshToken
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