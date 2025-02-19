package com.dubaiculture.utils.interceptors

import android.content.Context
import android.provider.Settings
import com.dubaiculture.utils.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class HeaderInterceptor @Inject constructor(
    private val context: Context,
    private val sessionManager: SessionManager
) :
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
//        !url.contains("Register") &&
        if (!url.contains("RefreshToken")
            && !url.contains("MobileLogin")
            && !url.contains("ConfirmAccount")
            && !url.contains("ValidateOTP")
            && !url.contains("ResendVerification")
            && !url.contains("ForgotPassword")
            && !url.contains("GuestLogin")
            && !url.contains("UAEPassLogin")
            && !url.contains("UAEPassLinkAccount")
            && !url.contains("UAEPassCreateAccount")
            && !url.contains("Auth/Register")
            && !url.contains("_vti_bin/DCAAPI")
        ) {
            //All the Above End Points Will be Ignored on the this stage
            nonBlockingUrl = true
        }


        if (nonBlockingUrl) {
            runBlocking {
                val pair = sessionManager.getToken()
                //first => isUserLoggedIn?
                //second => if logged in then add token as header
                if (pair.first) {
                    requestBuilder.addHeader("Authorization", "Bearer ${pair.second}")
                    if (request.header("Guest-Token") != null) {
                        requestBuilder.removeHeader("Guest-Token")
                    }
                } else {
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
                if (request.header("Authorization") != null) {
                    requestBuilder.removeHeader("Authorization")
                }
            }
        }

        request = requestBuilder.build()
        //chain.proceed() calls next interceptor if chained in okHttpClientbuilder
        return chain.proceed(request)
    }
}