package com.app.dubaiculture.data.repository.trip.remote

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponse
import com.app.dubaiculture.data.repository.login.service.LoginService
import com.app.dubaiculture.data.repository.settings.remote.response.UserSettingResponse
import com.app.dubaiculture.data.repository.trip.remote.response.DurationResponse
import com.app.dubaiculture.data.repository.trip.remote.response.InterestedInResponse
import com.app.dubaiculture.data.repository.trip.remote.response.NearestLocationResponse
import com.app.dubaiculture.data.repository.trip.remote.response.UserTypeResponse
import com.app.dubaiculture.data.repository.trip.service.TripService
import javax.inject.Inject

class TripRDS @Inject constructor(private val tripService: TripService) : BaseRDS() {

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

}