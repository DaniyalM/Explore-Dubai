package com.dubaiculture.data.repository.attraction.service

import com.dubaiculture.data.repository.attraction.remote.response.AttractionResponse
import com.dubaiculture.data.repository.base.BaseService
import retrofit2.http.GET
import retrofit2.http.Query

interface AttractionService : BaseService {
//    @GET("/Content/GetAttractionCategories")
//    suspend fun getAttractionCategoryApi(@Body attractionCategoryRequestDTO: AttractionCategoryRequestDTO): AttractionResponse

    @GET("/api/Content/GetAttractionCategories")
    suspend fun getAttractionCategoryApi(@Query("culture") culture: String): AttractionResponse

    @GET("/api/Content/GetAttractionDetails")
    suspend fun getAttractionDetail(
        @Query("Id") attractionId: String,
        @Query("culture") culture: String,
    ): AttractionResponse
    @GET("/api/Content/GetAttraction360Image")
    suspend fun getAttractionDetailForThreeSixty(
        @Query("Id") attractionId: String,
        @Query("culture") culture: String,
    ): AttractionResponse

    @GET("/api/Content/GetAttractionsById")
    suspend fun getAttractionsListingByCategory(
        @Query("Id") attractionCatId: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
        @Query("culture") culture: String,
    ): AttractionResponse

    @GET("Content/GetVisitedPlace")
    suspend fun getVisitedPlaces(
            @Query("culture") culture: String
    ): AttractionResponse
}