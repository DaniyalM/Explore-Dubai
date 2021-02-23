package com.app.dubaiculture.data.repository.explore.local.models

data class Attractions(
    val id: Int,
    val title: String= "",
    val category: String= "",
    val is_liked: Boolean=false,
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
