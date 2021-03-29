package com.app.dubaiculture.data.repository.user.di

import com.app.dubaiculture.data.repository.ApplicationDatabase
import com.app.dubaiculture.data.repository.user.local.UserDao
import com.app.dubaiculture.data.repository.user.local.guest.GuestDao
import com.app.dubaiculture.data.repository.user.service.RefreshTokenService
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
    fun provideUserDao(appDatabase: ApplicationDatabase): UserDao = appDatabase.userDao()

    @Provides
    fun provideGuestDao(appDatabase: ApplicationDatabase): GuestDao = appDatabase.guestDao()

    @Provides
    fun provideUserService(retrofit: Retrofit): RefreshTokenService =
        retrofit.create(RefreshTokenService::class.java)

}