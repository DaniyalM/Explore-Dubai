package com.app.dubaiculture.data.repository.attraction.service

import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AttractionService {
//    @GET("/Content/GetAttractionCategories")
//    suspend fun getAttractionCategoryApi(@Body attractionCategoryRequestDTO: AttractionCategoryRequestDTO): AttractionResponse

    @GET("/api/Content/GetAttractionCategories")
    suspend fun getAttractionCategoryApi(@Query("culture") culture: String): AttractionResponse

    @GET("/attraction/{attraction_id}")
    suspend fun getAttractionDetail(
        @Path("attraction_id") attractionId: String,
        @Query("culture") culture: String,
    ): AttractionResponse

    @GET("/api/Content/GetAttractionsById")
    suspend fun getAttractionsListingByCategory(
        @Query("Id") attractionCatId: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
        @Query("culture") culture: String,
    ): AttractionResponse
}