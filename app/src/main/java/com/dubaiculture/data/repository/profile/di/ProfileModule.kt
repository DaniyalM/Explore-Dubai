package com.dubaiculture.data.repository.profile.di

import com.dubaiculture.data.repository.ApplicationDatabase
import com.dubaiculture.data.repository.profile.local.ProfileDao
import com.dubaiculture.data.repository.profile.service.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit


@Module
@InstallIn(ViewModelComponent::class)
object ProfileModule {
    @Provides
    fun provideProfileService(retrofit: Retrofit): ProfileService =
            retrofit.create(ProfileService::class.java)

    @Provides
    fun provideProfileDao(appDatabase: ApplicationDatabase): ProfileDao =
        appDatabase.profileDao()
}