package com.app.dubaiculture.data.repository.attraction.di

import com.app.dubaiculture.data.repository.attraction.service.AttractionService
import com.app.dubaiculture.data.repository.base.BaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object AttractionModule {

    @Provides
    fun provideAttractionService(retrofit: Retrofit): AttractionService =
        retrofit.create(AttractionService::class.java)

    @Provides
    fun provideBaseService(retrofit: Retrofit): BaseService =
        retrofit.create(BaseService::class.java)
}