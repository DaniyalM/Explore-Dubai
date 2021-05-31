package com.app.dubaiculture.data.repository.user.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    var userId: String,
    var email: String,
    var phoneNumber: String,
    var userName: String,
    var userImage: String,
    var token: String,
    var expireIn: Int?,
    var refreshToken: String,
    @ColumnInfo(name = "created_at") val createdAt: Long=System.currentTimeMillis(),
)



