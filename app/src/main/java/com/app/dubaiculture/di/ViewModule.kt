package com.app.dubaiculture.di

import android.content.Context
import com.app.dubaiculture.data.repository.attraction.AttractionRepository
import com.app.dubaiculture.data.repository.visited.VisitedRepository
import com.app.dubaiculture.utils.BeaconUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        visitedRepository: VisitedRepository
    ) =
        BeaconUtils(context, visitedRepository)
}