package com.dubaiculture.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase

import com.dubaiculture.data.repository.login.local.UaeDAO
import com.dubaiculture.data.repository.login.local.UAEPass
import com.dubaiculture.data.repository.photo.local.Photo
import com.dubaiculture.data.repository.photo.local.PhotoDao
import com.dubaiculture.data.repository.profile.local.Profile
import com.dubaiculture.data.repository.profile.local.ProfileDao
import com.dubaiculture.data.repository.user.local.User
import com.dubaiculture.data.repository.user.local.UserDao
import com.dubaiculture.data.repository.user.local.guest.Guest
import com.dubaiculture.data.repository.user.local.guest.GuestDao

@Database(
    entities = [Photo::class, User::class, Guest::class, Profile::class,
        UAEPass::class
    ], version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun profileDao(): ProfileDao
    abstract fun userDao(): UserDao
    abstract fun guestDao(): GuestDao
    abstract fun uaeDao(): UaeDAO

}
