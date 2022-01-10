package com.dubaiculture.di

import android.content.Context
import com.dubaiculture.data.repository.visited.VisitedRepository
import com.dubaiculture.utils.BeaconUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModule {
//    @Singleton
//    @Provides
//    fun provideGroupieAdapter()=GroupAdapter<GroupieViewHolder>()

    @Singleton
    @Provides
    fun provideBeaconUtils(
        @ApplicationContext context: Context,
//        visitedRepository: VisitedRepository
    ) =
        BeaconUtils(context)

    @Singleton
    @Provides
    fun provideDate() = SimpleDateFormat("yyyy-MM-dd", Locale.US)
}