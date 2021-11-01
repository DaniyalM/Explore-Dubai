package com.app.dubaiculture.data.repository.trip.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.settings.remote.response.UserSettingResponse
import com.app.dubaiculture.data.repository.trip.remote.request.EventAttractionRequestDTO
import com.app.dubaiculture.data.repository.trip.remote.request.SaveTripRequestDTO
import com.app.dubaiculture.data.repository.trip.remote.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TripService : BaseService {

    @GET("/api/Trip/UserType")
    suspend fun getUserType(): UserTypeResponse

    @GET("/api/Trip/InterestedIn")
    suspend fun getInterestedIn(): InterestedInResponse

    @GET("/api/Trip/NearestLocation")
    suspend fun getNearestLocation(): NearestLocationResponse

    @GET("/api/Trip/Duration")
    suspend fun getDurations(): DurationResponse

    @POST("/api/Trip/EventsAndAttractions")
    suspend fun postEventAttraction(@Body eventAttractionRequestDTO: EventAttractionRequestDTO): EventAttractionResponse

    @POST("/api/Trip/SaveTrip")
    suspend fun saveTrip(@Body saveTripRequestDTO: SaveTripRequestDTO): SaveTripResponse

}