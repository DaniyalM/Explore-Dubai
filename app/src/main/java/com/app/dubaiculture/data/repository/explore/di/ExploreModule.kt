package com.app.dubaiculture.data.repository.explore.di

import com.app.dubaiculture.data.repository.explore.service.ExploreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object ExploreModule {

    @Provides
    fun provideExploreService(retrofit: Retrofit): ExploreService =
        retrofit.create(ExploreService::class.java)
}