package com.app.dubaiculture.data.repository.news.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.news.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService : BaseService {
    @GET("Content/GetLatestNews")
    suspend fun getLatestNews(
            @Query("pageNo") pageNumber: Int,
            @Query("pageSize") pageSize: Int,
            @Query("culture") culture: String,
    ): NewsResponse
}