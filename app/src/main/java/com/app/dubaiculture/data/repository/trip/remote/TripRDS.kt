package com.app.dubaiculture.data.repository.trip.remote

import android.webkit.WebStorage
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.remote.AttractionPagingSource
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.trip.remote.request.EventAttractionRequestDTO
import com.app.dubaiculture.data.repository.trip.remote.request.SaveTripRequestDTO
import com.app.dubaiculture.data.repository.trip.remote.response.*
import com.app.dubaiculture.data.repository.trip.service.MapService
import com.app.dubaiculture.data.repository.trip.service.TripService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TripRDS @Inject constructor(
    private val tripService: TripService,
    private val mapService: MapService
) : BaseRDS() {

    suspend fun getUserType(): Result<UserTypeResponse> = safeApiCall {
        tripService.getUserType()
    }

    suspend fun getInterestedIn(): Result<InterestedInResponse> = safeApiCall {
        tripService.getInterestedIn()
    }

    suspend fun getNearestLocation(): Result<NearestLocationResponse> = safeApiCall {
        tripService.getNearestLocation()
    }

    suspend fun getDurations(): Result<DurationResponse> = safeApiCall {
        tripService.getDurations()
    }

    suspend fun postEventAttraction(eventAttractionRequestDTO: EventAttractionRequestDTO): Result<EventAttractionResponse> =
        safeApiCall {
            tripService.postEventAttraction(eventAttractionRequestDTO)
        }

    suspend fun getDirections(map: HashMap<String, String>): Result<DirectionResponse> =
        safeApiCall {
            mapService.getDirections(map)
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

}