package com.app.dubaiculture.data.repository.trip.remote.response

data class InterestedInResponseDTO(
    val AllIcon: String,
    val AllTitle: String,
    val InterestedIn: List<InterestedIn>,
    val Title: String
)

data class InterestedIn(
    val ColorClass: String,
    val Icon: String,
    val Id: String,
    val Image: String,
    val Title: String
)