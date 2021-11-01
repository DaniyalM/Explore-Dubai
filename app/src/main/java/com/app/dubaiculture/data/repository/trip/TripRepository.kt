package com.app.dubaiculture.data.repository.trip
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.trip.local.*
import com.app.dubaiculture.data.repository.trip.mapper.*
import com.app.dubaiculture.data.repository.trip.remote.TripRDS
import com.app.dubaiculture.data.repository.trip.remote.request.EventAttractionRequest
import com.app.dubaiculture.data.repository.trip.remote.request.SaveTripRequest
import com.app.dubaiculture.data.repository.trip.remote.response.DirectionResponse
import com.app.dubaiculture.data.repository.trip.remote.response.SaveTripResponse
import com.app.dubaiculture.utils.event.Event
import com.app.dubaiculture.utils.security.rds.SecurityManagerRDS
import javax.inject.Inject

class TripRepository @Inject constructor(
    private val tripRDS: TripRDS,
    private val securityManager: SecurityManagerRDS
) :
    BaseRepository() {

    suspend fun getUserType(): Result<UserTypes> =
        when (val resultRds = tripRDS.getUserType()) {
            is Result.Success -> {
                if (resultRds.value.succeeded) {
                    Result.Success(transformUserType(resultRds.value.userTypeResponseDTO))
                } else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Error -> resultRds
            is Result.Failure -> resultRds
        }

    suspend fun getInterestedIn(): Result<Event<InterestedIn>> =
        when (val resultRds = tripRDS.getInterestedIn()) {
            is Result.Success -> {
                if (resultRds.value.succeeded) {
                    Result.Success(Event(transformInterestedIn(resultRds.value.interestedInResponseDTO)))
                } else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Error -> resultRds
            is Result.Failure -> resultRds
        }

    suspend fun getNearestLocation(): Result<NearestLocation> =
        when (val resultRds = tripRDS.getNearestLocation()) {
            is Result.Success -> {
                if (resultRds.value.succeeded) {
                    Result.Success(transformNearestLocation(resultRds.value.nearestLocationResponseDTO))
                } else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Error -> resultRds
            is Result.Failure -> resultRds
        }

    suspend fun getDurations(): Result<Durations> =
        when (val resultRds = tripRDS.getDurations()) {
            is Result.Success -> {
                if (resultRds.value.succeeded) {
                    Result.Success(transformDurations(resultRds.value.durationResponseDTO))
                } else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Error -> resultRds
            is Result.Failure -> resultRds
        }

    suspend fun postEventAttraction(eventAttractionRequest: EventAttractionRequest): Result<EventAttractions> =
        when (val resultRds = tripRDS.postEventAttraction(transformEventAttractionRequest(eventAttractionRequest))) {
            is Result.Success -> {
                if (resultRds.value.succeeded) {
                    Result.Success(transformEventAttractionResponse(resultRds.value.eventAttractionResponseDTO))
                } else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Error -> resultRds
            is Result.Failure -> resultRds
        }

    suspend fun getDirections(map:HashMap<String,String>): Result<DirectionResponse> =
        when (val resultRds = tripRDS.getDirections(map)) {
            is Result.Success -> {
                if (resultRds.value.status == "OK") {
                    Result.Success(resultRds.value)
                } else {
                    Result.Failure(false, null, null, "Error")
                }
            }
            is Result.Error -> resultRds
            is Result.Failure -> resultRds
        }

    suspend fun saveTrip(saveTripRequest: SaveTripRequest): Result<SaveTripResponse> =
        when (val resultRds = tripRDS.saveTrip(transformSaveTripRequest(saveTripRequest))) {
            is Result.Success -> {
                if (resultRds.value.succeeded) {
                    Result.Success((resultRds.value))
                } else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Error -> resultRds
            is Result.Failure -> resultRds
        }

}