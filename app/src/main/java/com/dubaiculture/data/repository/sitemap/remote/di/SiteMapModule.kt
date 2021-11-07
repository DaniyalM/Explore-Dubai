package com.dubaiculture.data.repository.sitemap.remote.di

import com.dubaiculture.data.repository.sitemap.service.SiteMapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object SiteMapModule {
    @Provides
    fun provideSiteMapService(retrofit: Retrofit): SiteMapService =
        retrofit.create(SiteMapService::class.java)

}