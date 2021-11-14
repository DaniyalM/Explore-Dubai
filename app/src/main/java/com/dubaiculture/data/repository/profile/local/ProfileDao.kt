package com.dubaiculture.data.repository.profile.local

import androidx.room.Dao
import androidx.room.Query
import com.dubaiculture.data.repository.base.BaseDao


@Dao
interface ProfileDao : BaseDao<Profile> {
    @Query("SELECT * FROM profile")
    suspend fun getProfile(): List<Profile>


}