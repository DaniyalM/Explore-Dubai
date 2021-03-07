package com.app.dubaiculture.data.repository.attraction.service

import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryResponse
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDetailResponse
import retrofit2.http.*

interface AttractionService {
    @POST("/category/attractions")
    suspend fun getAttractionCategoryApi(@Body attractionCategoryRequestDTO: AttractionCategoryRequestDTO): AttractionCategoryResponse

    @GET("/attraction/{attraction_id}")
    suspend fun getAttractionDetail(
        @Path("attraction_id") attractionId: String,
        @Query("culture") culture: String,
    ): AttractionDetailResponse
}