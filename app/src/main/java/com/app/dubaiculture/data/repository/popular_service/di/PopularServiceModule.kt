package com.app.dubaiculture.data.repository.popular_service.di

import com.app.dubaiculture.data.repository.popular_service.service.PopularService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object PopularServiceModule {
    @Provides
    fun providePopularService(retrofit: Retrofit): PopularService =
        retrofit.create(PopularService::class.java)
}