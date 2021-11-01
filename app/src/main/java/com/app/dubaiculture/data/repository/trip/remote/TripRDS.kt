package com.app.dubaiculture.data.repository.trip.remote

import android.webkit.WebStorage
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponse
import com.app.dubaiculture.data.repository.login.service.LoginService
import com.app.dubaiculture.data.repository.settings.remote.response.UserSettingResponse
import com.app.dubaiculture.data.repository.trip.remote.request.EventAttractionRequestDTO
import com.app.dubaiculture.data.repository.trip.remote.request.SaveTripRequestDTO
import com.app.dubaiculture.data.repository.trip.remote.response.*
import com.app.dubaiculture.data.repository.trip.service.MapService
import com.app.dubaiculture.data.repository.trip.service.TripService
import javax.inject.Inject

class TripRDS @Inject constructor(private val tripService: TripService,private val mapService: MapService) : BaseRDS() {

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

    suspend fun postEventAttraction(eventAttractionRequestDTO: EventAttractionRequestDTO): Result<EventAttractionResponse> = safeApiCall {
        tripService.postEventAttraction(eventAttractionRequestDTO)
    }

    suspend fun getDirections(map:HashMap<String,String>): Result<DirectionResponse> = safeApiCall {
        mapService.getDirections(map)
    }

    suspend fun saveTrip(saveTripRequestDTO: SaveTripRequestDTO) :Result<SaveTripResponse> = safeApiCall {
        tripService.saveTrip(saveTripRequestDTO)
    }

}