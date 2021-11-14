package com.dubaiculture.data.repository.user.local.guest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Guest(
    @PrimaryKey var token:String,
    var ExpiresIn:Int?,
    @ColumnInfo(name = "created_at") val createdAt: Long=System.currentTimeMillis()
)