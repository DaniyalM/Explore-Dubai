package com.app.dubaiculture.data.repository.explore.service

import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequestDTO
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreResponse
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.photo.remote.response.PhotosResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ExploreService {
    @GET("/api/Content/HomeScreenContent")
    suspend fun getExploreApi(@Query("culture") culture:String): ExploreResponse
}