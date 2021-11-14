package com.dubaiculture.data.repository.explore.di

import com.dubaiculture.data.repository.explore.service.ExploreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ExploreModule {

    @Provides
    fun provideExploreService(retrofit: Retrofit): ExploreService =
        retrofit.create(ExploreService::class.java)
}