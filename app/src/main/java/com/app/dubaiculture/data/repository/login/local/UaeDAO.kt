package com.app.dubaiculture.data.repository.login.local

import androidx.room.Dao
import androidx.room.Query
import com.app.dubaiculture.data.repository.base.BaseDao
import com.app.dubaiculture.data.repository.user.local.User

@Dao
interface UaeDAO : BaseDao<UaeLoginRequest> {
    @Query("SELECT * FROM uaeloginrequest")
    suspend fun   getAll(): List<UaeLoginRequest>
}