package com.app.dubaiculture.data.repository.trip.di

import com.app.dubaiculture.data.repository.trip.service.MapService
import com.app.dubaiculture.data.repository.trip.service.TripService
import com.app.dubaiculture.di.GoogleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object TripModule {
    @Provides
    fun provideTripService(retrofit: Retrofit): TripService =
        retrofit.create(TripService::class.java)
    @Provides
    fun provideRetrofitForMaps(@GoogleApi retrofit: Retrofit): MapService =
        retrofit.create(MapService::class.java)

}