package com.app.dubaiculture.data.repository.trip.local

data class InterestedIn(
    val title: String,
    val interestedInList: List<InterestedInType>
)

data class InterestedInType(
    val id: String,
    val checked: Boolean,
    val image: String,
    val icon: String,
    val title: String
)