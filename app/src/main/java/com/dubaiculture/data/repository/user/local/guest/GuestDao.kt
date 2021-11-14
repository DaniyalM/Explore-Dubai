package com.dubaiculture.data.repository.user.local.guest

import androidx.room.Dao
import androidx.room.Query
import com.dubaiculture.data.repository.base.BaseDao
import com.dubaiculture.data.repository.user.local.guest.Guest
@Dao
interface GuestDao : BaseDao<Guest> {
    @Query(value = "SELECT * From guest")
    suspend fun getGuestAll():List<Guest>
}