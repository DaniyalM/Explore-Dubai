package com.dubaiculture.data.repository.trip.remote.response

import com.dubaiculture.data.repository.trip.remote.request.DateTimeFilterDTO

data class EventAttractionResponseDTO(
    val Attractions: List<AttractionDTO>,
    val Events: List<EventDTO>,
    val EventsAndAttractions: List<EventsAndAttractionDTO>,
    val Location: LocationDTO,
    val DayAndNightTime: DayAndNightTimeDTO?,
    val DateTimeFilter: List<DateTimeFilterDTO>?= mutableListOf(),
    val TripID: String
)

data class DayAndNightTimeDTO(
    val DayTime: String,
    val NightTime: String
)



data class AttractionDTO(
    val BusyDays: List<BusyDay>,
    val Category: String?,
    val CategoryDestinationIcon: String?,
    val CategoryID: String?,
    val CategoryTripIcon: String?,
    val DateFrom: String?,
    val DateTo: String?,
    val Day: String?,
    val DayFrom: DayFrom,
    val DayTo: DayTo,
    val DetailPageUrl: String?,
    val DisplayTimeFrom: String?,
    val DisplayTimeTo: String?,
    val ID: String?,
    val Image: String?,
    val IsAttraction: Boolean?,
    val IsEvent: Boolean?,
    val Latitude: String?,
    val LocationTitle: String?,
    val Longitude: String?,
    val MapLink: String?,
    val SecondaryCategory: String?,
    val SecondaryCategoryID: String?,
    val Summary: String?,
    val TimeFrom: String?,
    val TimeTo: String?,
    val Title: String?,
    val icon: String?
)

data class EventDTO(
    val BusyDays: List<BusyDayX>,
    val Category: String?,
    val CategoryDestinationIcon: String?,
    val CategoryID: String?,
    val CategoryTripIcon: String?,
    val DateFrom: String?,
    val DateTo: String?,
    val Day: String?,
    val DayFrom: DayFromX,
    val DayTo: DayToX,
    val DetailPageUrl: String?,
    val DisplayTimeFrom: String?,
    val DisplayTimeTo: String?,
    val ID: String?,
    val Image: String?,
    val IsAttraction: Boolean?,
    val IsEvent: Boolean?,
    val Latitude: String?,
    val LocationTitle: String?,
    val Longitude: String?,
    val MapLink: String?,
    val SecondaryCategory: String?,
    val SecondaryCategoryID: String?,
    val Summary: String?,
    val TimeFrom: String?,
    val TimeTo: String?,
    val Title: String?,
    val icon: String?
)

data class EventsAndAttractionDTO(
    val BusyDays: List<BusyDayXX>,
    val Category: String?,
    val CategoryDestinationIcon: String?,
    val CategoryID: String?,
    val CategoryTripIcon: String?,
    val DateFrom: String?,
    val DateTo: String?,
    val Day: String?,
    val DayFrom: DayFromXX,
    val DayTo: DayToXX,
    val DetailPageUrl: String?,
    val DisplayTimeFrom: String?,
    val DisplayTimeTo: String?,
    val ID: String?,
    val Image: String?,
    val IsAttraction: Boolean?,
    val IsEvent: Boolean?,
    val Latitude: String?,
    val LocationTitle: String?,
    val Longitude: String?,
    val MapLink: String?,
    val SecondaryCategory: String?,
    val SecondaryCategoryID: String?,
    val Summary: String?,
    val TimeFrom: String?,
    val TimeTo: String?,
    val Title: String?,
    val icon: String?
)

data class LocationDTO(
    val Icon: String?,
    val Latitude: String?,
    val Location: String?,
    val LocationId: String?,
    val LocationTitle: String?,
    val Longitude: String?,
    val CustomLocation: Boolean?
)

data class BusyDay(
    val Number: String?,
    val Title: String?
)

data class DayFrom(
    val Number: String?,
    val Title: String?
)

data class DayTo(
    val Number: String?,
    val Title: String?
)

data class BusyDayX(
    val Number: String?,
    val Title: String?
)

data class DayFromX(
    val Number: String?,
    val Title: String?
)

data class DayToX(
    val Number: String?,
    val Title: String?
)

data class BusyDayXX(
    val Number: String?,
    val Title: String?
)

data class DayFromXX(
    val Number: String?,
    val Title: String?
)

data class DayToXX(
    val Number: String?,
    val Title: String?
)