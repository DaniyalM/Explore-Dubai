package com.app.dubaiculture.data.repository.viewgallery.remote.di

import com.app.dubaiculture.data.repository.viewgallery.service.ViewGalleryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object ViewGalleryModule {
    @Provides
    fun provideViewGalleryService(retrofit: Retrofit): ViewGalleryService =
        retrofit.create(ViewGalleryService::class.java)

}