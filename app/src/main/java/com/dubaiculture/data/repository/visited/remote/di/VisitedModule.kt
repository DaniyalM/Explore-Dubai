package com.dubaiculture.data.repository.visited.remote.di

import com.dubaiculture.data.repository.visited.remote.service.VisitedService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object VisitedModule {
    @Provides
    fun provideVisitedService(retrofit: Retrofit): VisitedService =
        retrofit.create(VisitedService::class.java)
}