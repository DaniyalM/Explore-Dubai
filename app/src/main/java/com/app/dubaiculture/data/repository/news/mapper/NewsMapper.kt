package com.app.dubaiculture.data.repository.news.mapper

import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.data.repository.news.remote.request.LatestNewsDTO
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequest
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequestDTO
import com.app.dubaiculture.data.repository.news.remote.response.NewsResponse


fun transformNewsRequest(newsRequest: NewsRequest)=NewsRequestDTO(
        pageNo = newsRequest.pageNumber,
        pageSize = newsRequest.pageSize,
        culture = newsRequest.culture
)


fun transformLatestNewsResponse(newsResponse: NewsResponse): List<LatestNews> =
        newsResponse.Result.latest.run {
            transformNewsResponse(this)
        }

fun transformNewsResponse(newsResponse: NewsResponse): List<LatestNews> =
        newsResponse.Result.news.run {
            transformNewsResponse(this)
        }


fun transformNewsResponse(newsDTO: List<LatestNewsDTO>) = newsDTO.map {

    LatestNews(
            id = it.id,
            title = it.title,
            postedDate = it.postedDate,
            image = it.image,
            date = it.date
    )

}
