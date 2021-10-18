package com.app.dubaiculture.data.repository.trip.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.settings.remote.response.UserSettingResponse
import com.app.dubaiculture.data.repository.trip.remote.response.UserTypeResponse
import retrofit2.http.GET

interface TripService : BaseService {

    @GET("/api/Trip/UserType")
    suspend fun getUserType(): UserTypeResponse

}