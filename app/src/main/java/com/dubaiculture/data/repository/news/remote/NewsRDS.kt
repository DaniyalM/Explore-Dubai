package com.dubaiculture.data.repository.news.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.news.remote.request.LatestNewsDTO
import com.dubaiculture.data.repository.news.remote.request.NewsFilterRequestDTO
import com.dubaiculture.data.repository.news.remote.request.NewsRequestDTO
import com.dubaiculture.data.repository.news.remote.service.NewsService
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

    suspend fun getTags(culture: String) =
            safeApiCall {
                newsService.getNewsTags(
                    culture
                )
            }

    suspend fun getFilterNews(newsFilterRequestDTO: NewsFilterRequestDTO) =
            safeApiCall {
                newsService.getSearchNewsDetail(newsFilterRequestDTO)
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