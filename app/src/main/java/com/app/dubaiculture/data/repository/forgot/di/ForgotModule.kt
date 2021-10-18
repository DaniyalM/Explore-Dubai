package com.app.dubaiculture.data.repository.forgot.di

import com.app.dubaiculture.data.repository.forgot.service.ForgotService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ForgotModule {

    @Provides
    fun provideForgotService(retrofit: Retrofit): ForgotService =
        retrofit.create(ForgotService::class.java)
}