package com.app.dubaiculture.data.repository.event.service

import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionResponse
import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequestDTO
import com.app.dubaiculture.data.repository.event.remote.request.EventFiltersRequestDTO
import com.app.dubaiculture.data.repository.event.remote.response.AddToFavouriteResponse
import com.app.dubaiculture.data.repository.event.remote.response.EventResponse
import com.app.dubaiculture.data.repository.event.remote.response.ScheduleResponse
import retrofit2.http.*

interface EventService : BaseService {

    @GET("/api/Content/GetEvents")
    suspend fun getEvents(@Query("culture") culture: String): EventResponse

    @POST("/api/Content/SearchEvents")
    suspend fun getEventsWithFilters(@Body eventFiltersRequestDTO: EventFiltersRequestDTO): EventResponse


    @GET("/api/Content/GetEventFilters")
    suspend fun getEventFilters(@Query("culture") culture: String): EventResponse


    @GET("/api/Content/GetEventDetails")
    suspend fun getEventDetail(
        @Query("id") eventId: String,
        @Query("culture") culture: String,
    ): EventResponse



//    @GET("/api/Content/GetEventDetails")
//    suspend fun getEventDetails(
//        @Query("Id") attractionId: String,
//        @Query("culture") culture: String,
//    ): EventResponse
}
