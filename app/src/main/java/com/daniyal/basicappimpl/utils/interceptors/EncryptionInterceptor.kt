package com.example.basearchitecture.common.Utils.interceptors

import android.os.Build
import androidx.annotation.RequiresApi
import com.daniyal.basicappimpl.BuildConfig
import com.example.basearchitecture.common.Utils.SecurityManager
import com.example.basearchitecture.common.Utils.requestBodyToString
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class EncryptionInterceptor @Inject constructor(val securityManager: SecurityManager) :
    Interceptor {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (BuildConfig.IS_ENCRYPTION_ENABLED && !request.method.equals("GET")) {
            val rawBody = request.body
            var encryptedBody = ""
            val mediaType: MediaType = "text/plain; charset=utf-8".toMediaTypeOrNull()!!
            try {
                val rawBodyString: String = requestBodyToString(rawBody)!!
                Timber.e("Request Body %s", rawBodyString)
                encryptedBody = securityManager.encrypt(rawBodyString)
                Timber.e("Encrypted Request Body %s", encryptedBody)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            val body: RequestBody = RequestBody.create(mediaType, encryptedBody)

            //build new request
            request = request.newBuilder()
                .header("Content-Type", body.contentType().toString())
                .header("Content-Length", body.contentLength().toString())
                .method(request.method, body).build()

        }

        return chain.proceed(request)
    }
}
//https://medium.com/swlh/okhttp-interceptors-with-retrofit-2dcc322cc3f3