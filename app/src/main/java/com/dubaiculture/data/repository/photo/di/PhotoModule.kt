package com.dubaiculture.data.repository.photo.di

import com.dubaiculture.data.repository.ApplicationDatabase
import com.dubaiculture.data.repository.photo.local.PhotoDao
import com.dubaiculture.data.repository.photo.remote.service.PhotoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object PhotoModule {

    @Provides
    fun providePhotoService(retrofit: Retrofit): PhotoService =
        retrofit.create(PhotoService::class.java)


    @Provides
    fun providePhotoDao(appDatabase: ApplicationDatabase): PhotoDao =
        appDatabase.photoDao()

}