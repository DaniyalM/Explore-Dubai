package com.app.dubaiculture.data.repository.user.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey
    var userId: String,
    var email: String,
    var phoneNumber: String,
    var userName: String,
    var userImage: String,
    var userImageUri: String? = null,
    var token: String,
    var expireIn: Int?,
    var refreshToken: String,
    var idn: String?=null,
    var verificationToken: String,
    var hasPassword: Boolean = false,
    @ColumnInfo(name = "created_at") var createdAt: Long = System.currentTimeMillis(),
):Parcelable



