package com.app.dubaiculture.data.repository.attraction.service

import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionResponse
import retrofit2.http.*

interface AttractionService {
    @POST("/category/attractions")
    suspend fun getAttractionCategoryApi(@Body attractionCategoryRequestDTO: AttractionCategoryRequestDTO): AttractionResponse

    @GET("/attraction/{attraction_id}")
    suspend fun getAttractionDetail(
        @Path("attraction_id") attractionId: String,
        @Query("culture") culture: String,
    ): AttractionResponse

    @GET("/Content/GetAttractionsById")
    suspend fun getAttractionsByCategory(
        @Path("Id") attractionCatId: String,
        @Query("culture") culture: String,
    ): AttractionResponse
}