package com.app.dubaiculture.data.repository.news.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequestDTO
import com.app.dubaiculture.data.repository.news.service.NewsService
import javax.inject.Inject

class NewsRDS @Inject constructor(val newsService: NewsService) : BaseRDS(newsService) {
    suspend fun getNews(newsRequestDTO: NewsRequestDTO) =
            safeApiCall {
                newsService.getLatestNews(
                        newsRequestDTO.pageNo,
                        newsRequestDTO.pageSize,
                        newsRequestDTO.culture
                )
            }


    suspend fun getNewsDetails(newsRequestDTO: NewsRequestDTO) =
        safeApiCall {
            newsService.getNewsDetail(
                newsRequestDTO.id!!,
                newsRequestDTO.culture
            )
        }
}