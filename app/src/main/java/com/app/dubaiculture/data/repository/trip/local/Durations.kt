package com.app.dubaiculture.data.repository.trip.local


data class Durations(
    val daysList: List<Day>,
    val hoursList: List<Hour>,
    val dayAndNightTime: DayAndNightTime
)

data class DayAndNightTime(
    val dayTime: String,
    val nightTime: String
)

data class Day(
    val id: Int,
    val duration: String
)

data class Hour(
    val id: Int,
    val duration: String
)