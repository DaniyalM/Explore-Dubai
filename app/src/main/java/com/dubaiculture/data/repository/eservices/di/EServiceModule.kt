package com.dubaiculture.data.repository.eservices.di

import com.dubaiculture.data.repository.eservices.service.EService
import com.dubaiculture.data.repository.trip.service.TripService
import com.dubaiculture.di.EServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object EServiceModule {
    @Provides
    fun provideEService(@EServices retrofit: Retrofit): EService =
        retrofit.create(EService::class.java)
}