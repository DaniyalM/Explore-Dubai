package com.dubaiculture.data.repository.trip.remote.request

class EventAttractionRequest(
    val category: List<String>,
    val culture: String,
    val date: List<String>,
    val location: String,
    val customLatitude: String,
    val customLongitude: String,
    val save:Boolean

)