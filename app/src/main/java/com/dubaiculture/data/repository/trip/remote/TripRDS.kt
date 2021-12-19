package com.dubaiculture.data.repository.trip.remote

import android.webkit.WebStorage
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.attraction.remote.AttractionPagingSource
import com.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.trip.remote.request.EventAttractionRequestDTO
import com.dubaiculture.data.repository.trip.remote.request.SaveTripRequestDTO
import com.dubaiculture.data.repository.trip.remote.response.*
import com.dubaiculture.data.repository.trip.service.MapService
import com.dubaiculture.data.repository.trip.service.TripService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TripRDS @Inject constructor(
    private val tripService: TripService,
    private val mapService: MapService
) : BaseRDS() {

    suspend fun getUserType(culture: String): Result<UserTypeResponse> = safeApiCall {
        tripService.getUserType(culture)
    }

    suspend fun getInterestedIn(culture: String): Result<InterestedInResponse> = safeApiCall {
        tripService.getInterestedIn(culture)
    }

    suspend fun getNearestLocation(culture: String): Result<NearestLocationResponse> = safeApiCall {
        tripService.getNearestLocation(culture)
    }

    suspend fun getDurations(culture: String): Result<DurationResponse> = safeApiCall {
        tripService.getDurations(culture)
    }

    suspend fun postEventAttraction(eventAttractionRequestDTO: EventAttractionRequestDTO): Result<EventAttractionResponse> =
        safeApiCall {
            tripService.postEventAttraction(eventAttractionRequestDTO)
        }

    suspend fun getDirections(map: HashMap<String, String>): Result<DirectionResponse> =
        safeApiCall {
            mapService.getDirections(map)
        }

    suspend fun getDistance(map: HashMap<String, String>): Result<DistanceMatrixResponse> =
        safeApiCall {
            mapService.getDistance(map)
        }


    suspend fun saveTrip(saveTripRequestDTO: SaveTripRequestDTO): Result<SaveTripResponse> =
        safeApiCall {
            tripService.saveTrip(saveTripRequestDTO)
        }

    suspend fun getMyTrips(culture: String): Result<Flow<PagingData<Trip>>> = safeApiCall {
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MyTripPagingSource(tripService, culture) }
        ).flow
    }

    suspend fun getTripDetails(tripId: String, culture: String): Result<EventAttractionResponse> =
        safeApiCall {
            tripService.getTripDetails(tripId, culture)
        }

    suspend fun deleteTrip(tripId: String): Result<SaveTripResponse> =
        safeApiCall {
            tripService.deleteTrip(tripId)
        }

    suspend fun getTripCount(culture: String): Result<MyTripCountResponse> =
        safeApiCall {
            tripService.getTripCount(culture)
        }

}