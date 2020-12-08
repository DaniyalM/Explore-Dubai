package com.app.dubaiculture.data.repository.photo.local

import androidx.room.Dao
import androidx.room.Query
import com.app.dubaiculture.data.repository.base.BaseDao


@Dao
interface PhotoDao : BaseDao<Photo> {
    @Query("SELECT * FROM photo")
    suspend fun getAll(): List<Photo>

}