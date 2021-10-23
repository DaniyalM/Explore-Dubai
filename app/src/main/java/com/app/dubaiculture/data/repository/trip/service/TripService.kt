package com.app.dubaiculture.data.repository.trip.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.settings.remote.response.UserSettingResponse
import com.app.dubaiculture.data.repository.trip.remote.response.DurationResponse
import com.app.dubaiculture.data.repository.trip.remote.response.InterestedInResponse
import com.app.dubaiculture.data.repository.trip.remote.response.NearestLocationResponse
import com.app.dubaiculture.data.repository.trip.remote.response.UserTypeResponse
import retrofit2.http.GET

interface TripService : BaseService {

    @GET("/api/Trip/UserType")
    suspend fun getUserType(): UserTypeResponse

    @GET("/api/Trip/InterestedIn")
    suspend fun getInterestedIn(): InterestedInResponse

    @GET("/api/Trip/NearestLocation")
    suspend fun getNearestLocation(): NearestLocationResponse

    @GET("/api/Trip/Duration")
    suspend fun getDurations(): DurationResponse

}