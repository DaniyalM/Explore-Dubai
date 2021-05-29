package com.app.dubaiculture.data.repository.profile.di

import com.app.dubaiculture.data.repository.news.service.NewsService
import com.app.dubaiculture.data.repository.profile.service.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit


@Module
@InstallIn(ActivityComponent::class)
object ProfileModule {
    @Provides
    fun provideProfileService(retrofit: Retrofit): ProfileService =
            retrofit.create(ProfileService::class.java)
}