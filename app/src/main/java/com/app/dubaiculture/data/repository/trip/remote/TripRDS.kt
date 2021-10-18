package com.app.dubaiculture.data.repository.trip.remote

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.trip.remote.response.UserTypeResponse
import com.app.dubaiculture.data.repository.trip.service.TripService
import javax.inject.Inject

class TripRDS @Inject constructor(private val tripService: TripService) : BaseRDS() {

    suspend fun getUserType(): Result<UserTypeResponse> = safeApiCall {
        tripService.getUserType()
    }

}