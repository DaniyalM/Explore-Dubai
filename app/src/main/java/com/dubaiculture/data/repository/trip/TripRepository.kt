package com.dubaiculture.data.repository.trip

import androidx.paging.PagingData
import androidx.paging.map
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.attraction.local.models.Attractions
import com.dubaiculture.data.repository.attraction.mapper.transformAttractionDetail
import com.dubaiculture.data.repository.attraction.mapper.transformAttractionsRequest
import com.dubaiculture.data.repository.attraction.remote.request.AttractionRequest
import com.dubaiculture.data.repository.base.BaseRepository
import com.dubaiculture.data.repository.trip.local.*
import com.dubaiculture.data.repository.trip.mapper.*
import com.dubaiculture.data.repository.trip.remote.TripRDS
import com.dubaiculture.data.repository.trip.remote.request.EventAttractionRequest
import com.dubaiculture.data.repository.trip.remote.request.SaveTripRequest
import com.dubaiculture.data.repository.trip.remote.response.DirectionResponse
import com.dubaiculture.data.repository.trip.remote.response.DistanceMatrixResponse
import com.dubaiculture.data.repository.trip.remote.response.SaveTripResponse
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.event.Event
import com.dubaiculture.utils.security.rds.SecurityManagerRDS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
        when (val resultRds =
            tripRDS.postEventAttraction(transformEventAttractionRequest(eventAttractionRequest))) {
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

    suspend fun getDirections(map: HashMap<String, String>): Result<DirectionResponse> =
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

    suspend fun getDistance(map: HashMap<String, String>): Result<DistanceMatrixResponse> =
        when (val resultRds = tripRDS.getDistance(map)) {
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

    suspend fun getMyTrips(culture: String): Result<Flow<PagingData<Trip>>> {
        val result = tripRDS.getMyTrips(culture)
        return if (result is Result.Success) {
            Result.Success(result.value.map {
                it.map {
                    transformMyTripResponse(it)
                }
            })

        } else {
            Result.Failure(true, null, null, Constants.Error.SOMETHING_WENT_WRONG)
        }
    }

    suspend fun getTripDetails(tripId: String, culture: String): Result<EventAttractions> =
        when (val resultRds =
            tripRDS.getTripDetails(tripId, culture)) {
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

    suspend fun deleteTrip(tripId:String): Result<SaveTripResponse> =
        when (val resultRds = tripRDS.deleteTrip(tripId)) {
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