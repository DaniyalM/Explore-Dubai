package com.app.dubaiculture.data.repository.attraction.local.models

data class Attractions(
    val id: String,
    val title: String= "",
    val category: String= "",
    val IsFavourite: Boolean=false,
    var locationTitle: String? = "",
    var location: String? = "",
    var portraitImage: String? = "",
    var landscapeImage: String? = "",
    var description: String? = "",
    var startTime: String? = "",
    var endTime: String? = "",
    var endDay: String? = "",
    var startDay: String? = "",
    var color: String? = ""
)
