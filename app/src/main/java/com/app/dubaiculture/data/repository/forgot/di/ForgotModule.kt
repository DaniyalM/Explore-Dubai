package com.app.dubaiculture.data.repository.registeration.di

import com.app.dubaiculture.data.repository.forgot.remote.service.ForgotService
import com.app.dubaiculture.data.repository.login.remote.service.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object ForgotModule {
    @Provides
    fun provideForgotService(retrofit: Retrofit): ForgotService =
        retrofit.create(ForgotService::class.java)
}