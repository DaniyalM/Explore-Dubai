package com.app.dubaiculture.data.repository.search.remote.service

import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionResponse
import com.app.dubaiculture.data.repository.search.remote.request.SearchRequestDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchService {
    @GET("Content/GetSearchHistory")
    suspend fun getSearchHistory(
        @Query("culture") culture: String?,
        @Query("UserID") userId: String
    ): AttractionResponse

    @POST("Content/GetSearchHistory")
    suspend fun clearHistory(
        @Body searchRequestDTO: SearchRequestDTO
    ): AttractionResponse

}