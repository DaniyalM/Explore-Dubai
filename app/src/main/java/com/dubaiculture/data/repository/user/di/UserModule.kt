package com.dubaiculture.data.repository.user.di

import com.dubaiculture.data.repository.ApplicationDatabase
import com.dubaiculture.data.repository.login.local.UaeDAO
import com.dubaiculture.data.repository.user.local.UserDao
import com.dubaiculture.data.repository.user.local.guest.GuestDao
import com.dubaiculture.data.repository.user.service.RefreshTokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    fun provideUaeDao(appDatabase: ApplicationDatabase): UaeDAO = appDatabase.uaeDao()

    @Provides
    fun provideUserDao(appDatabase: ApplicationDatabase): UserDao = appDatabase.userDao()

    @Provides
    fun provideGuestDao(appDatabase: ApplicationDatabase): GuestDao = appDatabase.guestDao()

    @Provides
    fun provideUserService(retrofit: Retrofit): RefreshTokenService =
        retrofit.create(RefreshTokenService::class.java)

}