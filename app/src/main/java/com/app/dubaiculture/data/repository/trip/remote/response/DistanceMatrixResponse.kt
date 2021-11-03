package com.app.dubaiculture.data.repository.trip.remote.response

data class DistanceMatrixResponse(
    val destination_addresses: List<String>,
    val origin_addresses: List<String>,
    val rows: List<Row>,
    val status: String
)

data class Row(
    val elements: List<Element>
)

data class Element(
    val distance: TextValue,
    val duration: TextValue,
    val duration_in_traffic: TextValue,
    val status: String
)

data class TextValue(
    val text: String,
    val value: Int
)

