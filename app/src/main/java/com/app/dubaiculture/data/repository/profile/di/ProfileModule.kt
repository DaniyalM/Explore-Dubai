package com.app.dubaiculture.data.repository.profile.di

import com.app.dubaiculture.data.repository.ApplicationDatabase
import com.app.dubaiculture.data.repository.news.service.NewsService
import com.app.dubaiculture.data.repository.photo.local.PhotoDao
import com.app.dubaiculture.data.repository.profile.local.ProfileDao
import com.app.dubaiculture.data.repository.profile.service.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
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