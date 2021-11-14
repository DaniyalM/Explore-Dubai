package com.dubaiculture.data.repository.trip.local

data class MyTrips(
    val trips: List<Trip>
)

data class Trip(
    val count: Int,
    val endDate: String,
    val tripId: String,
    val images: List<String>,
    val name: String,
    val startDate: String
)