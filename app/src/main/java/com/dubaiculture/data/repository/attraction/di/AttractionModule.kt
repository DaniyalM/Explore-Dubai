package com.dubaiculture.data.repository.attraction.di

import com.dubaiculture.data.repository.attraction.service.AttractionService
import com.dubaiculture.data.repository.base.BaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object AttractionModule {

    @Provides
    fun provideAttractionService(retrofit: Retrofit): AttractionService =
        retrofit.create(AttractionService::class.java)

    @Provides
    fun provideBaseService(retrofit: Retrofit): BaseService =
        retrofit.create(BaseService::class.java)
}