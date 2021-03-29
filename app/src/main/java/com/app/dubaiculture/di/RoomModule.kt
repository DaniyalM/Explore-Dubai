package com.app.dubaiculture.di

import android.content.Context
import androidx.room.Room
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.data.repository.ApplicationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideRoomDb(@ApplicationContext context: Context): ApplicationDatabase =
        Room.databaseBuilder(
            context,
            ApplicationDatabase::class.java, BuildConfig.DB_NAME
        ).build()
}