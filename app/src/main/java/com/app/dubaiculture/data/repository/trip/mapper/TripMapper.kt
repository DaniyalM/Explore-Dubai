package com.app.dubaiculture.data.repository.trip.mapper

import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.trip.local.*
import com.app.dubaiculture.data.repository.trip.local.Day
import com.app.dubaiculture.data.repository.trip.local.DayAndNightTime
import com.app.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.app.dubaiculture.data.repository.trip.local.Hour
import com.app.dubaiculture.data.repository.trip.local.InterestedIn
import com.app.dubaiculture.data.repository.trip.local.Location
import com.app.dubaiculture.data.repository.trip.local.NearestLocation
import com.app.dubaiculture.data.repository.trip.remote.request.EventAttractionRequest
import com.app.dubaiculture.data.repository.trip.remote.request.EventAttractionRequestDTO
import com.app.dubaiculture.data.repository.trip.remote.response.*

fun transform(userTypeResponse: UserTypeResponse): UserTypeResponseDTO {
    return UserTypeResponseDTO(
        Title = userTypeResponse.userTypeResponseDTO.Title,
        UsersType = userTypeResponse.userTypeResponseDTO.UsersType
    )
}

fun transformUserType(userTypeResponse: UserTypeResponseDTO)=UserTypes(
    title = userTypeResponse.Title,
    usersType = userTypeResponse.UsersType.mapIndexed { index, usersTypeDTO ->
        UsersType(
            id = index +1,
            image = usersTypeDTO.Image,
            title = usersTypeDTO.Title,
            checked = false
        )
    }
)

fun transformInterestedIn(interestedInResponseDTO: InterestedInResponseDTO) = InterestedIn(
    title = interestedInResponseDTO.Title,
    interestedInList = interestedInResponseDTO.InterestedIn.map {  interestedInResponseDTO ->
        InterestedInType(
            id = interestedInResponseDTO.Id,
            image = interestedInResponseDTO.Image,
            title = interestedInResponseDTO.Title,
            icon = interestedInResponseDTO.Icon,
            checked = false
        )
    }
)

fun transformNearestLocation(nearestLocationResponseDTO: NearestLocationResponseDTO) =
    NearestLocation(
        title = nearestLocationResponseDTO.Title,
        nearestLocation = nearestLocationResponseDTO.NearestLocation.map { nearestLocation ->
            LocationNearest(
                latitude = nearestLocation.Latitude,
                longitude = nearestLocation.Longitude,
                locationTitle = nearestLocation.LocationTitle,
                locationId = nearestLocation.LocationId,
                isChecked = false
            )
        }
    )

fun transformDurations(durationsResponseDTO: DurationResponseDTO) =
    Durations(
        daysList = durationsResponseDTO.Days.mapIndexed { index, day ->
            Day(
                id = index + 1,
                duration = day.Duration
            )
        },
        hoursList = durationsResponseDTO.Hours.mapIndexed { index, hour ->
            Hour(
                id = index + 1,
                duration = hour.Duration
            )
        },
        dayAndNightTime = DayAndNightTime(
            dayTime = durationsResponseDTO.DayAndNightTime.DayTime,
            nightTime = durationsResponseDTO.DayAndNightTime.NightTime
        )
    )

fun transformEventAttractionRequest(eventAttractionRequest: EventAttractionRequest) =
    EventAttractionRequestDTO(
        Category = eventAttractionRequest.category,
        Culture = eventAttractionRequest.culture,
        Date = eventAttractionRequest.date,
        Location = eventAttractionRequest.location,
    )

fun transformEventAttractionResponse(eventAttractionResponseDTO: EventAttractionResponseDTO) =
    EventAttractions(
        eventsAndAttractions = eventAttractionResponseDTO.EventsAndAttractions.map { eventsAndAttraction ->
            EventsAndAttraction(
                busyDays = eventsAndAttraction.BusyDays.map {
                    EADay(
                        it.Number?:"",
                        it.Title?:""
                    )
                },
                category = eventsAndAttraction.Category?:"",
                categoryDestinationIcon = eventsAndAttraction.CategoryDestinationIcon?:"",
                categoryID = eventsAndAttraction.CategoryID?:"",
                categoryTripIcon = eventsAndAttraction.CategoryTripIcon?:"",
                dateFrom = eventsAndAttraction.DateFrom?:"",
                dateTo = eventsAndAttraction.DateTo?:"",
                day = eventsAndAttraction.Day?:"",
                dayFrom = EADay(
                    number = eventsAndAttraction.DayFrom.Number?:"",
                    title = eventsAndAttraction.DayFrom.Title?:""
                ),
                dayTo = EADay(
                    number = eventsAndAttraction.DayTo.Number?:"",
                    title = eventsAndAttraction.DayTo.Title?:""
                ),
                detailPageUrl = eventsAndAttraction.DetailPageUrl?:"",
                displayTimeFrom = eventsAndAttraction.DisplayTimeFrom?:"",
                displayTimeTo = eventsAndAttraction.DisplayTimeTo?:"",
                id = eventsAndAttraction.ID?:"",
                image = eventsAndAttraction.Image?:"",
                isAttraction = eventsAndAttraction.IsAttraction?:false,
                isEvent = eventsAndAttraction.IsEvent?:false,
                latitude = eventsAndAttraction.Latitude?:"",
                locationTitle = eventsAndAttraction.LocationTitle?:"",
                longitude = eventsAndAttraction.Longitude?:"",
                mapLink = eventsAndAttraction.MapLink?:"",
                secondaryCategory = eventsAndAttraction.SecondaryCategory?:"",
                secondaryCategoryID = eventsAndAttraction.SecondaryCategoryID?:"",
                summary = eventsAndAttraction.Summary?:"",
                timeFrom = eventsAndAttraction.TimeFrom?:"",
                timeTo = eventsAndAttraction.TimeTo?:"",
                title = eventsAndAttraction.Title?:"",
                icon = eventsAndAttraction.icon?:"",
            )
        },
        location = Location(
            latitude = eventAttractionResponseDTO.Location.Latitude?:"",
            locationId = eventAttractionResponseDTO.Location.LocationId?:"",
            locationTitle = eventAttractionResponseDTO.Location.LocationTitle?:"",
            longitude = eventAttractionResponseDTO.Location.Longitude?:"",
        )
    )
