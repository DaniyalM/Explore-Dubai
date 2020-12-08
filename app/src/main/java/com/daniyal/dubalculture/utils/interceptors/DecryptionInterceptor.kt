package com.daniyal.dubalculture.utils.interceptors

import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresApi
import com.daniyal.dubalculture.BuildConfig
import com.daniyal.dubalculture.utils.security.rds.SecurityManagerRDS
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import timber.log.Timber
import javax.inject.Inject

class DecryptionInterceptor @Inject constructor(private val securityManager: SecurityManagerRDS) :
    Interceptor {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (BuildConfig.IS_ENCRYPTION_ENABLED) {

            val response = chain.proceed(request)

            if (response.isSuccessful) {
                val newResponse = response.newBuilder()
                var contentType = response.header("Content-Type")
                if (TextUtils.isEmpty(contentType)) contentType = "application/json"
                val responseString = response.body!!.string()
//                var responseString = response.body!!.string()
                // fixed encrypted response for testing
//                responseString = "qhILOoGd77qYgfeUCW0H+oJaH6eXAsM7fQY9b6fEMwgBqywrYS5iTtetB0HKP5Wp"
                var decryptedString: String? = null
                try {
                    decryptedString = securityManager.decrypt(responseString)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val mediaType: MediaType = contentType.toString().toMediaTypeOrNull()!!

                Timber.e("Decrypted Response Body %s", decryptedString)

                newResponse.body(decryptedString?.toResponseBody(mediaType))
//                        ResponseBody.create(mediaType, decryptedString!!))
                return newResponse.build()
            }
            return response
        }
        return chain.proceed(request)

    }
}
//https://medium.com/swlh/okhttp-interceptors-with-retrofit-2dcc322cc3f3