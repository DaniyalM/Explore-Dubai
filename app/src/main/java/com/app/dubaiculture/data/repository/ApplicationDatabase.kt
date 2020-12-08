package com.app.dubaiculture.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.dubaiculture.data.repository.photo.local.Photo
import com.app.dubaiculture.data.repository.photo.local.PhotoDao

@Database(entities = [Photo::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

}
