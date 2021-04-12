package com.app.dubaiculture.data.repository.sitemap.remote.di

import com.app.dubaiculture.data.repository.sitemap.service.SiteMapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object SiteMapModule {
    @Provides
    fun provideSiteMapService(retrofit: Retrofit): SiteMapService =
        retrofit.create(SiteMapService::class.java)

}