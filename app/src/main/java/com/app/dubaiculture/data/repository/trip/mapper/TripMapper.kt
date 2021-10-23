package com.app.dubaiculture.data.repository.trip.mapper

import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.trip.local.*
import com.app.dubaiculture.data.repository.trip.local.Day
import com.app.dubaiculture.data.repository.trip.local.DayAndNightTime
import com.app.dubaiculture.data.repository.trip.local.Hour
import com.app.dubaiculture.data.repository.trip.local.InterestedIn
import com.app.dubaiculture.data.repository.trip.local.NearestLocation
import com.app.dubaiculture.data.repository.trip.remote.response.*

fun transform(userTypeResponse: UserTypeResponse): UserTypeResponseDTO {
    return UserTypeResponseDTO(
        Title = userTypeResponse.userTypeResponseDTO.Title,
        UsersType = userTypeResponse.userTypeResponseDTO.UsersType
    )
}

fun transformUserType(userTypeResponse: UserTypeResponseDTO) = UserTypes(
    title = userTypeResponse.Title,
    usersType = userTypeResponse.UsersType.mapIndexed { index, usersTypeDTO ->
        UsersType(
            id = index + 1,
            image = usersTypeDTO.Image,
            title = usersTypeDTO.Title,
            checked = false
        )
    }
)

fun transformInterestedIn(interestedInResponseDTO: InterestedInResponseDTO) = InterestedIn(
    title = interestedInResponseDTO.Title,
    interestedInList = interestedInResponseDTO.InterestedIn.mapIndexed { index, interestedInResponseDTO ->
        InterestedInType(
            id = index + 1,
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