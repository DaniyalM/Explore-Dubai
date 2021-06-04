package com.app.dubaiculture.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.dubaiculture.data.repository.photo.local.Photo
import com.app.dubaiculture.data.repository.photo.local.PhotoDao
import com.app.dubaiculture.data.repository.profile.local.Profile
import com.app.dubaiculture.data.repository.profile.local.ProfileDao
import com.app.dubaiculture.data.repository.user.local.User
import com.app.dubaiculture.data.repository.user.local.UserDao
import com.app.dubaiculture.data.repository.user.local.guest.Guest
import com.app.dubaiculture.data.repository.user.local.guest.GuestDao

@Database(entities = [Photo::class, User::class, Guest::class,Profile::class], version = 3)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun profileDao(): ProfileDao
    abstract fun userDao(): UserDao
    abstract fun guestDao(): GuestDao

}
