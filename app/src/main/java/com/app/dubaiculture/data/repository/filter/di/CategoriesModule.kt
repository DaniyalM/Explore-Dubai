package com.app.dubaiculture.data.repository.filter.di

import com.app.dubaiculture.data.repository.filter.service.CategoriesService
import com.app.dubaiculture.data.repository.forgot.service.ForgotService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
@Module
@InstallIn(ActivityComponent::class)
object CategoriesModule {

    @Provides
    fun provideCategoriesService(retrofit: Retrofit): CategoriesService =
        retrofit.create(CategoriesService::class.java)
}