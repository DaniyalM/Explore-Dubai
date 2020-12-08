package com.app.dubalculture.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object ViewModule {
//    @Singleton
//    @Provides
//    fun provideGroupieAdapter()=GroupAdapter<GroupieViewHolder>()
}