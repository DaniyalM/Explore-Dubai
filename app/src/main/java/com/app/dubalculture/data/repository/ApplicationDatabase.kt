package com.app.dubalculture.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.dubalculture.data.repository.photo.local.Photo
import com.app.dubalculture.data.repository.photo.local.PhotoDao

@Database(entities = [Photo::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

}
