package com.app.dubaiculture.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.dubaiculture.data.repository.photo.local.Photo
import com.app.dubaiculture.data.repository.photo.local.PhotoDao
import com.app.dubaiculture.data.repository.user.local.User
import com.app.dubaiculture.data.repository.user.local.UserDao

@Database(entities = [Photo::class, User::class], version = 2)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun userDao(): UserDao

}
