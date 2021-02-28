package com.app.dubaiculture.data.repository.explore.service

import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryResponse
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequestDTO
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ExploreService {
    @POST("/api/Content/HomeScreenContent")
    suspend fun getExploreApi(@Body exploreRequestDTO: ExploreRequestDTO): ExploreResponse

 }