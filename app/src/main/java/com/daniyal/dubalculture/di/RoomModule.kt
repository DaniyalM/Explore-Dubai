package com.daniyal.dubalculture.di

import android.content.Context
import androidx.room.Room
import com.daniyal.dubalculture.BuildConfig
import com.daniyal.dubalculture.data.repository.ApplicationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideRoomDb(@ApplicationContext context: Context): ApplicationDatabase =
        Room.databaseBuilder(
            context,
            ApplicationDatabase::class.java, BuildConfig.DB_NAME
        ).build()
}