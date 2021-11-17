package com.dubaiculture.data.repository.trip.remote.request

data class SaveTripRequestDTO(
    val DateTimeFilter: List<DateTimeFilterDTO>,
    val Name: String,
    val TripID: String
)

data class DateTimeFilterDTO(
    val Date: String,
    val Hours: String,
    val Type: String
)