package com.app.dubaiculture.data.repository.event.di

import com.app.dubaiculture.data.repository.event.service.EventService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit


@Module
@InstallIn(ActivityComponent::class)
object EventModule {

    @Provides
    fun provideEventService(retrofit: Retrofit): EventService =
        retrofit.create(EventService::class.java)
}