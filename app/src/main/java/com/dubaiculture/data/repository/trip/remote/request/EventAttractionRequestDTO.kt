package com.dubaiculture.data.repository.trip.remote.request

data class EventAttractionRequestDTO(
    val Category: List<String>,
    val Culture: String,
    val Date: List<String>,
    val Location: String,
    val CustomLatitude: String,
    val CustomLongitude: String,
    val Save:Boolean
)