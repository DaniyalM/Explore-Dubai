package com.app.dubaiculture.data.repository.user.local

import androidx.room.Dao
import androidx.room.Query
import com.app.dubaiculture.data.repository.base.BaseDao
@Dao
interface UserDao : BaseDao<User>{
    @Query("SELECT * FROM user")
    suspend fun   getAll(): List<User>
}