package com.dubaiculture.data.repository.trip.remote.response

data class MyTripResponseDTO(
    val Trips: List<Trip>
)

data class Trip(
    val Count: Int,
    val EndDate: String,
    val ID: String,
    val Images: List<String>,
    val Name: String,
    val StartDate: String
)