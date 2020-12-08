package com.daniyal.dubalculture.data.repository.photo.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
    @PrimaryKey
    val id: String,
    val desc: String,
    val likes: Int
)