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

        runBlocking {
            val pair = sessionManager.getToken()
            //first => isUserLoggedIn?
            //second => if logged in then add token as header
            if (pair.first) {
                requestBuilder.addHeader("Authorization", pair.second)
            }
            request = requestBuilder.build()
        }
        //chain.proceed() calls next interceptor if chained in okHttpClientbuilder
        return chain.proceed(request)
    }
}