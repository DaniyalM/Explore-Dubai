package com.app.dubaiculture.data.repository.trip.local


data class EventAttractions(
    val eventsAndAttractions: List<EventsAndAttraction>,
    val location: Location
)

data class EventsAndAttraction(
    val busyDays: List<EADay>,
    val category: String?,
    val categoryDestinationIcon: String?,
    val categoryID: String?,
    val categoryTripIcon: String?,
    val dateFrom: String?,
    val dateTo: String?,
    val day: String?,
    val dayFrom: EADay,
    val dayTo: EADay,
    val detailPageUrl: String?,
    val displayTimeFrom: String?,
    val displayTimeTo: String?,
    val id: String?,
    val image: String?,
    val isAttraction: Boolean?,
    val isEvent: Boolean?,
    val latitude: String?,
    val locationTitle: String?,
    val longitude: String?,
    val mapLink: String?,
    val secondaryCategory: String?,
    val secondaryCategoryID: String?,
    val summary: String?,
    val timeFrom: String?,
    val timeTo: String?,
    val title: String?,
    val icon: String?
)

data class Location(
    val latitude: String?,
    val locationId: String?,
    val locationTitle: String?,
    val longitude: String?,
)

data class EADay(
    val number: String?,
    val title: String?
)