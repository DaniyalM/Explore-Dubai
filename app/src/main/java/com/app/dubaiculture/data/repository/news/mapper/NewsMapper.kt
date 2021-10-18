package com.app.dubaiculture.data.repository.news.mapper

import com.app.dubaiculture.data.repository.news.local.*
import com.app.dubaiculture.data.repository.news.remote.request.*
import com.app.dubaiculture.data.repository.news.remote.response.NewsResponse


fun transformNewsTags(newsTagsDTO: NewsTagsDTO)=NewsTags(
    tag_id = newsTagsDTO.TagID?:"",
    tag_title = newsTagsDTO.TagTitle?:""
)
fun transformNewsFiltersRequest(newsFilterRequest: NewsFilterRequest) =
    NewsFilterRequestDTO(
        Culture = newsFilterRequest.culture ?: "en",
        Tag = newsFilterRequest.tags,
        Keyword = newsFilterRequest.keyword,
        DateFrom = newsFilterRequest.dateFrom,
        DateTo = newsFilterRequest.dateTo,
    )

fun transformNewsRequest(newsRequest: NewsRequest) = NewsRequestDTO(
    id = newsRequest.id,
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

fun transformNewsPaging(newsDTO: LatestNewsDTO) = LatestNews(
    id = newsDTO.id,
    title = newsDTO.title,
    postedDate = newsDTO.postedDate,
    image = newsDTO.image,
    date = newsDTO.date
)

fun transformNewsDetail(newsResponse: NewsResponse) = newsResponse.Result.detail.run {

    NewsDetail(
        id = ID,
        image = Image,
        description = Description,
        blockQuote = BlockQuote?.map {
            BlockQuote(
                summary = it.Summary,
                title = it.Title
            )
        },
        moreDetail = MoreDetail?.map {
            MoreDetail(
                description = it.Description,
                summary = it.Summary,
                title = it.Title
            )
        },
        relatedData = RelatedData?.map {
            LatestNews(
                id = it.id,
                title = it.title,
                postedDate = it.postedDate,
                image = it.image,
                date = it.date
            )
        },
        tags = Tags,
        twoColumnImageModule = TwoColumnImageModuleDTO?.map {
            TwoColumnImageModule(
                image1 = it.Image1,
                image2 = it.Image2

            )
        },
        postedDate = PostedDate,
        title = Title
    )
}


