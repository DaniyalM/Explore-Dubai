package com.dubaiculture.data.repository.trip.service

import com.dubaiculture.data.repository.trip.remote.response.DirectionResponse
import com.dubaiculture.data.repository.trip.remote.response.DistanceMatrixResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MapService {
    @GET("directions/json")
    suspend fun getDirections(
        @QueryMap options:Map<String, String>
    ): DirectionResponse

    @GET("distancematrix/json")
    suspend fun getDistance(
        @QueryMap options:Map<String, String>
    ): DistanceMatrixResponse

}