package com.app.dubaiculture.data.repository.sitemap.service

import com.app.dubaiculture.data.repository.sitemap.remote.response.SiteMapResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SiteMapService {
    @GET("/api/Content/GetAttractionIBeacon")
    suspend fun siteMap(
        @Query("id") attractionId: String,
        @Query("culture") culture: String,
    ): SiteMapResponse
}