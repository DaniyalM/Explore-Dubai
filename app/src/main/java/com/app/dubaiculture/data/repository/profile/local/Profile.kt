package com.app.dubaiculture.data.repository.profile.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey
    var email: String,
    var phoneNumber: String,
    var avatar: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
    )
