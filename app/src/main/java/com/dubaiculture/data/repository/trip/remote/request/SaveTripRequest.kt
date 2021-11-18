package com.dubaiculture.data.repository.trip.remote.request

data class SaveTripRequest(
    val name: String,
    val tripID: String,
    val dateTimeFilter: List<DateTimeFilter>,

    )

data class DateTimeFilter(
    val date: String,
    val hours: String,
    val type: String
)