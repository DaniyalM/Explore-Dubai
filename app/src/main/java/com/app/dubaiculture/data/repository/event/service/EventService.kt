package com.app.dubaiculture.data.repository.event.service

import com.app.dubaiculture.data.repository.event.remote.response.HomeEventResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EventService {

    @GET("/events")
    suspend fun getEvents(@Query("culture") culture: String):HomeEventResponse
}