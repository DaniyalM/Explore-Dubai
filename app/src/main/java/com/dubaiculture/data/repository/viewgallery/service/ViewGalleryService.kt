package com.dubaiculture.data.repository.viewgallery.service

import com.dubaiculture.data.repository.viewgallery.remote.response.ViewGalleryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ViewGalleryService {
    @GET("/api/Content/GetARMetadata")
    suspend fun getMetaData(
        @Query("Id") id: String,
        @Query("culture") culture: String,
    ): ViewGalleryResponse
}