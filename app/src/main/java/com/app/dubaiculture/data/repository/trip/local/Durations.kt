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

data class Duration(
    val id: Int,
    val dayDate: String,
    val hour: String,
    val isDay: Int,  //0 = default,1= day,2= night
    val isSelected: Boolean
)