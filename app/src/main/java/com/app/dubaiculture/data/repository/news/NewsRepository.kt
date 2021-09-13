package com.app.dubaiculture.data.repository.news

import androidx.paging.PagingData
import androidx.paging.map
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.data.repository.news.mapper.*
import com.app.dubaiculture.data.repository.news.remote.NewsRDS
import com.app.dubaiculture.data.repository.news.remote.request.NewsFilterRequest
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequest
import com.app.dubaiculture.utils.Constants.Error.SOMETHING_WENT_WRONG
import com.app.dubaiculture.utils.event.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsRDS: NewsRDS) : BaseRepository(newsRDS) {


    suspend fun getFilterNews(newsFilterRequest: NewsFilterRequest): Result<List<LatestNews>> {
        return  when (val result = newsRDS.getFilterNews(transformNewsFiltersRequest(newsFilterRequest))) {
            is Result.Success -> {
                Result.Success(transformNewsResponse(result.value))
            }
            is Result.Failure -> Result.Failure(true, null, null, SOMETHING_WENT_WRONG)
            is Result.Error -> Result.Failure(true, null, null, SOMETHING_WENT_WRONG)

        }
    }

    suspend fun getNews(newsRequest: NewsRequest): Result<Flow<PagingData<LatestNews>>> {
        val result = newsRDS.getPaginatedNews(transformNewsRequest(newsRequest))
        return if (result is Result.Success) {
            Result.Success(result.value.map {
                it.map {
                    transformNewsPaging(it)
                }
            })

        } else {
            Result.Failure(true, null, null, SOMETHING_WENT_WRONG)
        }
    }

    suspend fun getLatestNews(newsRequest: NewsRequest) =
        when (val result = newsRDS.getNews(transformNewsRequest(newsRequest))) {
            is Result.Success -> {
                if (result.value.succeeded) {
                    Result.Success(
                        transformLatestNewsResponse(result.value)
                    )
                } else {
                    Result.Failure(false, null, null, result.value.errorMessage)
                }
            }
            is Result.Error -> result
            is Result.Failure -> result

        }


    suspend fun getNewsDetails(newsRequest: NewsRequest) =
        when (val result = newsRDS.getNewsDetails(transformNewsRequest(newsRequest))) {
            is Result.Success -> {
                if (result.value.succeeded) {
                    Result.Success(Event(transformNewsDetail(result.value)))
                } else {
                    Result.Failure(false, null, null, result.value.errorMessage)
                }
            }
            is Result.Error -> result
            is Result.Failure -> result
        }


}