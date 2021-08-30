package com.app.dubaiculture.data.repository.event.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.event.remote.request.EventFiltersRequestDTO
import com.app.dubaiculture.data.repository.event.remote.response.EventResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("/api/Content/RegisterEvent")
    suspend fun getEventRegister(
        @Part("eventId") eventId: RequestBody,
        @Part("slotId") slotId: RequestBody,
        @Part("occupation") occupation: RequestBody,
        @Part Photo: MultipartBody.Part? = null
    ): EventResponse
}
