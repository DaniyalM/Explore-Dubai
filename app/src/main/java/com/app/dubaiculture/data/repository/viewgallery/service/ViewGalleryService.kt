package com.app.dubaiculture.data.repository.viewgallery.service

import com.app.dubaiculture.data.repository.sitemap.remote.response.SiteMapResponse
import com.app.dubaiculture.data.repository.viewgallery.remote.response.ViewGalleryResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ViewGalleryService {
    @GET("/api/Content/GetARMetadata")
    suspend fun getMetaData(
        @Query("Id") id: String,
        @Query("culture") culture: String,
    ): ViewGalleryResponse
}