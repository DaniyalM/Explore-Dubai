package com.app.dubaiculture.di

import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import com.app.dubaiculture.utils.location.LocationHelper
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context) =
        LocationServices.getFusedLocationProviderClient(context)


    @Singleton
    @Provides
    fun locationProvider(@ApplicationContext context: Context) =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @Singleton
    @Provides
    fun provideLocationRequest() = LocationRequest()

    @Singleton
    @Provides
    fun provideGeoCoder(@ApplicationContext context: Context) =
        Geocoder(context, Locale.getDefault())


    @Singleton
    @Provides
    fun provideLocationHelper(@ApplicationContext context: Context) =
        LocationHelper(provideFusedLocationProviderClient(context),
            provideLocationRequest())
}