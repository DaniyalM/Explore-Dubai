package com.dubaiculture.data.repository.news.remote.service

import com.dubaiculture.data.repository.base.BaseService
import com.dubaiculture.data.repository.event.remote.request.EventFiltersRequestDTO
import com.dubaiculture.data.repository.event.remote.response.EventResponse
import com.dubaiculture.data.repository.news.remote.request.NewsFilterRequestDTO
import com.dubaiculture.data.repository.news.remote.response.NewsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NewsService : BaseService {
    @GET("Content/GetLatestNews")
    suspend fun getLatestNews(
            @Query("pageNo") pageNumber: Int,
            @Query("pageSize") pageSize: Int,
            @Query("culture") culture: String,
    ): NewsResponse

    @GET("Content/GetNewsTags")
    suspend fun getNewsTags(
            @Query("culture") culture: String,
    ): NewsResponse

    @GET("Content/GetNewsDetails")
    suspend fun getNewsDetail(
            @Query("Id") id: String,
            @Query("culture") culture: String
    ): NewsResponse


    @POST("Content/SearchNews")
    suspend fun getSearchNewsDetail(@Body newsFilterRequestDTO: NewsFilterRequestDTO): NewsResponse



}