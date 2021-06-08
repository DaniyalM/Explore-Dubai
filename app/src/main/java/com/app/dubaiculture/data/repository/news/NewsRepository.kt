package com.app.dubaiculture.data.repository.news

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.news.local.News
import com.app.dubaiculture.data.repository.news.mapper.transformLatestNewsResponse
import com.app.dubaiculture.data.repository.news.mapper.transformNewsDetail
import com.app.dubaiculture.data.repository.news.mapper.transformNewsRequest
import com.app.dubaiculture.data.repository.news.mapper.transformNewsResponse
import com.app.dubaiculture.data.repository.news.remote.NewsRDS
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequest
import com.app.dubaiculture.utils.event.Event
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsRDS: NewsRDS) : BaseRepository(newsRDS) {


    suspend fun getLatestNews(newsRequest: NewsRequest) =
        when (val result = newsRDS.getNews(transformNewsRequest(newsRequest))) {
            is Result.Success -> {
                if (result.value.succeeded) {
                    Result.Success(
                        Event(
                            News(
                                latestNews = transformLatestNewsResponse(result.value),
                                news = transformNewsResponse(result.value)
                            )
                        )
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
                    Result.Success(Event(transformNewsDetail(result.value )))
                } else {
                    Result.Failure(false, null, null, result.value.errorMessage)
                }
            }
            is Result.Error -> result
            is Result.Failure -> result
        }


}