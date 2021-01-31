package com.app.dubaiculture.data.repository.explore.service

import com.app.dubaiculture.data.repository.explore.remote.response.ExploreResponse
import com.app.dubaiculture.data.repository.photo.remote.response.PhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ExploreService {
    @GET("/explore")
    suspend fun getExploreApi(): ExploreResponse
}