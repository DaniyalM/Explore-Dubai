package com.dubaiculture.data.repository.trip.service

import com.dubaiculture.data.repository.base.BaseService
import com.dubaiculture.data.repository.trip.remote.request.EventAttractionRequestDTO
import com.dubaiculture.data.repository.trip.remote.request.SaveTripRequestDTO
import com.dubaiculture.data.repository.trip.remote.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TripService : BaseService {

    @GET("/api/Trip/UserType")
    suspend fun getUserType(@Query("culture") culture: String): UserTypeResponse

    @GET("/api/Trip/InterestedIn")
    suspend fun getInterestedIn(@Query("culture") culture: String): InterestedInResponse

    @GET("/api/Trip/NearestLocation")
    suspend fun getNearestLocation(@Query("culture") culture: String): NearestLocationResponse

    @GET("/api/Trip/Duration")
    suspend fun getDurations(@Query("culture") culture: String): DurationResponse

    @POST("/api/Trip/EventsAndAttractions")
    suspend fun postEventAttraction(@Body eventAttractionRequestDTO: EventAttractionRequestDTO): EventAttractionResponse

    @POST("/api/Trip/SaveTrip")
    suspend fun saveTrip(@Body saveTripRequestDTO: SaveTripRequestDTO): SaveTripResponse

    @GET("/api/Trip/MyTrips")
    suspend fun getMyTrips(
        @Query("pageNo") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
        @Query("culture") culture: String
    ): MyTripsResponse

    @GET("/api/Trip/MyTripsDetail")
    suspend fun getTripDetails(
        @Query("TripId") tripId: String,
        @Query("culture") culture: String
    ): EventAttractionResponse

    @GET("/api/Trip/DeleteTrip")
    suspend fun deleteTrip(@Query("TripId") tripId: String): SaveTripResponse

    @GET("/api/Trip/MyTripsCount")
    suspend fun getTripCount(@Query("culture") culture: String): MyTripCountResponse

}