package com.app.dubaiculture.data.repository.trip.remote.response

data class DurationResponseDTO(
    val DayAndNightTime: DayAndNightTime,
    val Days: List<Day>,
    val Hours: List<Hour>,
    val NumberOfDays: String,
    val OR: String,
    val SelectDates: String,
    val Title: String
)

data class DayAndNightTime(
    val DayTime: String,
    val NightTime: String
)

data class Day(
    val Duration: String
)

data class Hour(
    val Duration: String
)