package com.dubaiculture.di

import android.content.Context
import com.dubaiculture.BuildConfig
import com.dubaiculture.utils.SessionManager
import com.dubaiculture.utils.interceptors.DecryptionInterceptor
import com.dubaiculture.utils.interceptors.EncryptionInterceptor
import com.dubaiculture.utils.interceptors.HeaderInterceptor
import com.dubaiculture.utils.security.tls.TLS
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    //Network Providers
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideHeaderInterceptor(
        @ApplicationContext context: Context,
        sessionManager: SessionManager,
    ) = HeaderInterceptor(context, sessionManager)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor,
        encryptionInterceptor: EncryptionInterceptor,
        decryptionInterceptor: DecryptionInterceptor,
        tls: TLS,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(encryptionInterceptor)
            .addInterceptor(decryptionInterceptor)
            .connectionSpecs(
                Arrays.asList(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.CLEARTEXT
                )
            )
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .cache(null)
        if (BuildConfig.IS_TSL_ENABLED) {
            val tlsConfig = tls.getConfig()
            builder.sslSocketFactory(tlsConfig.first, tlsConfig.second)
            //add custom hostNameVerifier if ssl gets hostname verification failed
        }

        return builder.build()

    }
//        if (RestConfig.DEBUG) { // debug ON
//            val logger = HttpLoggingInterceptor()
//            logger.level = HttpLoggingInterceptor.Level.BODY
//            OkHttpClient.Builder()
//                .addInterceptor(logger)
//                .readTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
//                .connectTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
//                .build()
//        } else // debug OFF
//            OkHttpClient.Builder()
//                .readTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
//                .connectTimeout(100, TimeUnit.SECONDS)
//                .build()


    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @GoogleApi
    fun provideRetrofitForMaps(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_MAP)
            .client(okHttpClient)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GoogleApi
