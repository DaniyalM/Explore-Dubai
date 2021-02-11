package com.app.dubaiculture.utils.interceptors

import com.app.dubaiculture.utils.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class HeaderInterceptor @Inject constructor(private val sessionManager: SessionManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestBuilder = request.newBuilder()
        val url = request.url.toString()

        if (!url.contains("RefreshToken")||
            !url.contains("MobileLogin")||
            !url.contains("Register")||
            !url.contains("ConfirmAccount")||
            !url.contains("ValidateOTP")||
            !url.contains("ResendVerification")||
            !url.contains("ForgotPassword")||
            !url.contains("SetPassword")||
            !url.contains("GuestLogin")
        ) {
            runBlocking {

                val pair = sessionManager.getToken()

                //first => isUserLoggedIn?
                //second => if logged in then add token as header
                if (pair.first) {
                    requestBuilder.addHeader("Authorization", "Bearer ${pair.second}")
                }
                requestBuilder.addHeader(
                    "Guest-Token",
                    "V0dL4ySaEJGVSj4CndEmITD13ET+hxFXCe7nkY66Cjd1GUnI40LBEAFIi3FvbVoorHdf5Dze8LDyNvQVTzVFbA=="
                )
                request = requestBuilder.build()
            }
        }

        //chain.proceed() calls next interceptor if chained in okHttpClientbuilder
        return chain.proceed(request)
    }
}