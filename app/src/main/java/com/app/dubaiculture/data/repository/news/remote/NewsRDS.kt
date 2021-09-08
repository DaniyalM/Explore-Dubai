package com.app.dubaiculture.data.repository.news.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.news.remote.request.LatestNewsDTO
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequestDTO
import com.app.dubaiculture.data.repository.news.service.NewsService
import com.app.dubaiculture.data.repository.photo.remote.response.PhotoDTO
import kotlinx.coroutines.flow.Flow
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


    suspend fun getPaginatedNews(newsRequestDTO: NewsRequestDTO): Result<Flow<PagingData<LatestNewsDTO>>> {
        return safeApiCall {
            Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { NewsPagingSource(newsService,newsRequestDTO) }
            ).flow
        }

    }


    suspend fun getNewsDetails(newsRequestDTO: NewsRequestDTO) =
        safeApiCall {
            newsService.getNewsDetail(
                newsRequestDTO.id!!,
                newsRequestDTO.culture
            )
        }
}