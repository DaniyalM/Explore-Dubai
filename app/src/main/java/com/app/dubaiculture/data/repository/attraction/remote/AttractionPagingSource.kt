package com.app.dubaiculture.data.repository.attraction.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.app.dubaiculture.data.repository.attraction.service.AttractionService
import com.app.dubaiculture.utils.Constants

class AttractionPagingSource(
    private val attractionService: AttractionService,
    private val attractionRequestDTO: AttractionRequestDTO,
) :
    PagingSource<Int, AttractionDTO>() {
    override fun getRefreshKey(state: PagingState<Int, AttractionDTO>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AttractionDTO> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = attractionService.getAttractionsListingByCategory(
                attractionCatId = attractionRequestDTO.attractionCategoryId!!,
                pageNumber = nextPageNumber,
                pageSize = Constants.PAGING.ATTRACTION_PAGING_SIZE * nextPageNumber,
                culture = attractionRequestDTO.culture
            )
            LoadResult.Page(
                data = response.Result.attractions,
                prevKey = null,
                nextKey = if (response.Result.attractions.size < Constants.PAGING.ATTRACTION_PAGING_SIZE) null else nextPageNumber.plus(
                    1
                )
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            LoadResult.Error(e)

        }
    }
}