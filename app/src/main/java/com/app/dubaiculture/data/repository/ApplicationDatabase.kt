package com.app.dubaiculture.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.dubaiculture.data.repository.login.local.UaeDAO
import com.app.dubaiculture.data.repository.login.local.UaeLoginRequest
import com.app.dubaiculture.data.repository.photo.local.Photo
import com.app.dubaiculture.data.repository.photo.local.PhotoDao
import com.app.dubaiculture.data.repository.profile.local.Profile
import com.app.dubaiculture.data.repository.profile.local.ProfileDao
import com.app.dubaiculture.data.repository.user.local.User
import com.app.dubaiculture.data.repository.user.local.UserDao
import com.app.dubaiculture.data.repository.user.local.guest.Guest
import com.app.dubaiculture.data.repository.user.local.guest.GuestDao

@Database(entities = [Photo::class, User::class, Guest::class,Profile::class,UaeLoginRequest::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun profileDao(): ProfileDao
    abstract fun userDao(): UserDao
    abstract fun guestDao(): GuestDao
    abstract fun uaeDao():UaeDAO

}
