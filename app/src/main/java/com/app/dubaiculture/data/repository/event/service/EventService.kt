package com.app.dubaiculture.data.repository.event.service

import com.app.dubaiculture.data.repository.event.remote.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventService {

    @GET("/events")
    suspend fun getEvents(@Query("culture") culture: String): EventResponse

    @GET("/events/{event_id}")
    suspend fun getEventDetail(
        @Path("event_id") eventId: String,
        @Query("culture") culture: String,
    ): EventResponse
}