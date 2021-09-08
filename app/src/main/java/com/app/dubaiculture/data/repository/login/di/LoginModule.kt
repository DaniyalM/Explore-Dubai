package com.app.dubaiculture.data.repository.registeration.di

import com.app.dubaiculture.data.repository.login.service.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

//    @Provides
//    fun provideExploreService(retrofit: Retrofit): ForgotService =
//        retrofit.create(ForgotService::class.java)
}