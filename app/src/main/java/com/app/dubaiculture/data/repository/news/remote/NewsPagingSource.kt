package com.app.dubaiculture.data.repository.news.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.dubaiculture.data.repository.news.remote.request.LatestNewsDTO
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequestDTO
import com.app.dubaiculture.data.repository.news.service.NewsService
import com.app.dubaiculture.data.repository.photo.remote.response.PhotoDTO
import com.app.dubaiculture.utils.Constants.PAGING.NEW_PAGING_SIZE

class NewsPagingSource(
    private val newsService: NewsService,
    private val newsRequestDTO: NewsRequestDTO,

    ) :
    PagingSource<Int, LatestNewsDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LatestNewsDTO> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = newsService.getLatestNews(
                pageNumber= nextPageNumber,
                pageSize = NEW_PAGING_SIZE * nextPageNumber,
                culture = newsRequestDTO.culture
            )
            LoadResult.Page(
                data = response.Result.news,
                prevKey = null,
                nextKey = if (response.Result.news.size < NEW_PAGING_SIZE) null else nextPageNumber.plus(
                    1
                )
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            LoadResult.Error(e)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, LatestNewsDTO>): Int? {
        return state.anchorPosition
    }
}
