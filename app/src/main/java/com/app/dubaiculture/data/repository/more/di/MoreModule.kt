package com.app.dubaiculture.data.repository.more.di

import com.app.dubaiculture.data.repository.more.service.MoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object MoreModule {
    @Provides
    fun provideMoreService(retrofit: Retrofit) = retrofit.create(MoreService::class.java)

}