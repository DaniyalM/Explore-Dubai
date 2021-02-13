package com.app.dubaiculture.utils.interceptors

import android.content.Context
import android.provider.Settings
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.utils.SessionManager
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class HeaderInterceptor @Inject constructor(private val context: Context,private val sessionManager: SessionManager) :
    Interceptor {

    var android_id: String = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )


    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestBuilder = request.newBuilder()
        val url = request.url.toString()
        var nonBlockingUrl = false
        var guestPass = false


        if (!url.contains("RefreshToken") || !url.contains("MobileLogin") ||
            !url.contains("Register") || !url.contains("ConfirmAccount") ||
            !url.contains("ValidateOTP") || !url.contains("ResendVerification") ||
            !url.contains("ForgotPassword")
        ) {
            nonBlockingUrl = true
        }
        if (url.contains("GuestLogin")){nonBlockingUrl = false}

        if (nonBlockingUrl) {
            runBlocking {
                val pair = sessionManager.getToken()
                //first => isUserLoggedIn?
                //second => if logged in then add token as header
                if (pair.first) {
                    requestBuilder.addHeader("Authorization", "Bearer ${pair.second}")
                } else {
                    if (request.header("Authorization") != null) {
                        requestBuilder.removeHeader("Authorization")
                    }
                    guestPass = true
                }
            }

        }

        if (guestPass) {
            runBlocking {
                val pairGuest = sessionManager.getGuestToken(android_id)
                pairGuest?.let {
                    requestBuilder.addHeader("Guest-Token", it.token)
                }
            }
        }

        request = requestBuilder.build()
        //chain.proceed() calls next interceptor if chained in okHttpClientbuilder
        return chain.proceed(request)
    }
}