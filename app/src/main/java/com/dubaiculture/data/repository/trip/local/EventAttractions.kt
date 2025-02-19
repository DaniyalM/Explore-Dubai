package com.dubaiculture.data.repository.trip.local

import com.dubaiculture.data.repository.trip.remote.request.DateTimeFilterDTO
import com.dubaiculture.data.repository.trip.remote.response.DayAndNightTimeDTO


data class EventAttractions(
    val eventsAndAttractions: List<EventsAndAttraction>,
    val location: Location,
    val tripId: String,
    val dayAndNightTime: DANTime,
    val dateTimeFilter: List<DTFilter>
)

data class DANTime(
    val dayTime: String,
    val nightTime: String
)

data class DTFilter(
    val date: String,
    val hours: String,
    val type: String
)

data class EventsAndAttraction(
    val busyDays: List<EADay>,
    val category: String,
    val categoryDestinationIcon: String,
    val categoryID: String,
    val categoryTripIcon: String,
    val dateFrom: String,
    val dateTo: String,
    val day: String,
//    val dayFrom: EADay,
//    val dayTo: EADay,
    val detailPageUrl: String,
    val displayTimeFrom: String,
    val displayTimeTo: String,
    val id: String,
    val image: String,
    val isAttraction: Boolean,
    val isEvent: Boolean,
    val latitude: String,
    val locationTitle: String,
    val longitude: String,
    val mapLink: String,
    val secondaryCategory: String,
    val secondaryCategoryID: String,
    val summary: String,
    val timeFrom: String,
    val timeTo: String,
    val title: String,
    val icon: String,
    val duration: String,
    val distance: String,
    val points:String,
    val distanceRadius: Double = 0.0,
    val travelMode: String,
)

data class Location(
    val latitude: String,
    val locationId: String,
    val locationTitle: String,
    val longitude: String,
    val customLocation: Boolean
)

data class EADay(
    val number: String,
    val title: String
)