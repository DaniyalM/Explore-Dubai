package com.app.dubaiculture.data.repository.news.di

import com.app.dubaiculture.data.repository.news.service.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object NewsModule {

    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsService =
            retrofit.create(NewsService::class.java)

}