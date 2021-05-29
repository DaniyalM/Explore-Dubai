package com.app.dubaiculture.data.repository.profile.local

import androidx.room.Dao
import androidx.room.Query
import com.app.dubaiculture.data.repository.base.BaseDao
import com.app.dubaiculture.data.repository.user.local.User


@Dao
interface ProfileDao : BaseDao<Profile> {
    @Query("SELECT * FROM profile")
    suspend fun   getProfile(): List<Profile>


}