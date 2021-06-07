package com.app.dubaiculture.data.repository.news.mapper

import com.app.dubaiculture.data.repository.news.local.*
import com.app.dubaiculture.data.repository.news.remote.request.LatestNewsDTO
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequest
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequestDTO
import com.app.dubaiculture.data.repository.news.remote.response.NewsResponse


fun transformNewsRequest(newsRequest: NewsRequest) = NewsRequestDTO(
    id=newsRequest.id,
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

fun transformNewsDetail(newsResponse: NewsResponse) = newsResponse.Result.detail.run {

    NewsDetail(
        id = ID,
        image = Image,
        description = Description,
        blockQuote = BlockQuoteDTO.map {
            BlockQuote(
                summary = it.Summary,
                title = it.Title
            )
        },
        moreDetail = MoreDetailDTO.map {
            MoreDetail(
                description = it.Description,
                summary = it.Summary,
                title = it.Title
            )
        },
        relatedData = RelatedData.map {
            LatestNews(
                id = it.id,
                title = it.title,
                postedDate = it.postedDate,
                image = it.image,
                date = it.date
            )
        },
        tags = Tags,
        twoColumnImageModule = TwoColumnImageModuleDTO.map {
            TwoColumnImageModule(
                image1 = it.Image1,
                image2 = it.Image2

            )
        },
        postedDate = PostedDate,
        title = Title
    )
}


