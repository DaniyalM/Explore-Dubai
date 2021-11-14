package com.dubaiculture.data.repository.filter.di

import com.dubaiculture.data.repository.filter.service.CategoriesService
import com.dubaiculture.data.repository.forgot.service.ForgotService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
@Module
@InstallIn(ViewModelComponent::class)
object CategoriesModule {

    @Provides
    fun provideCategoriesService(retrofit: Retrofit): CategoriesService =
        retrofit.create(CategoriesService::class.java)
}