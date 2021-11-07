package com.dubaiculture.data.repository.trip.remote.response

data class NearestLocationResponseDTO(
    val AddLocation: String,
    val CurrentLocation: String,
    val DefaultPin: String,
    val NearestLocation: List<NearestLocation>,
    val Title: String
)

data class NearestLocation(
    val Icon: String,
    val Location: String,
    val Latitude: String,
    val LocationId: String,
    val LocationTitle: String,
    val Longitude: String
)