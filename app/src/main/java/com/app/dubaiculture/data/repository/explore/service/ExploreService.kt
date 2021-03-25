package com.app.dubaiculture.data.repository.explore.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequestDTO
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface ExploreService :BaseService{
    @GET("/api/Content/HomeScreenContent")
    suspend fun getExploreApi(@Query("culture") culture: String): ExploreResponse
}