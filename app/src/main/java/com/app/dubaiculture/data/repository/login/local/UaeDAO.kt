package com.app.dubaiculture.data.repository.login.local

import androidx.room.Dao
import androidx.room.Query
import com.app.dubaiculture.data.repository.base.BaseDao

@Dao
interface UaeDAO : BaseDao<UAEPass> {
    @Query("SELECT * FROM uaepass")
    suspend fun   getAll(): List<UAEPass>
}