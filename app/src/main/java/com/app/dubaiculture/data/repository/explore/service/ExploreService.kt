package com.app.dubaiculture.data.repository.explore.service

import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequestDTO
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ExploreService {
    @GET("/api/Content/HomeScreenContent")
    suspend fun getExploreApi(@Body exploreRequestDTO: ExploreRequestDTO): ExploreResponse

}