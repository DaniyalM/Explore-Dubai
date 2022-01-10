package com.dubaiculture.data.repository.registeration.di

import com.dubaiculture.data.repository.ApplicationDatabase
import com.dubaiculture.data.repository.login.local.UaeDAO
import com.dubaiculture.data.repository.login.service.LoginService
import com.dubaiculture.data.repository.user.local.UserDao
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