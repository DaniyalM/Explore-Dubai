package com.dubaiculture.data.repository.search.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dubaiculture.data.repository.search.local.SearchResultItemDTO
import com.dubaiculture.data.repository.search.remote.request.SearchPaginationRequestDTO
import com.dubaiculture.data.repository.search.remote.service.SearchService
import com.dubaiculture.utils.Constants

class SearchPagingSource(
    private val searchService: SearchService,
    private val searchPaginationRequestDTO: SearchPaginationRequestDTO,
    private val callback: (count: Int) -> Unit,
    private val error: (message: String) -> Unit


) :
    PagingSource<Int, SearchResultItemDTO>() {
    override fun getRefreshKey(state: PagingState<Int, SearchResultItemDTO>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultItemDTO> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = searchService.searchResults(
                searchPaginationRequestDTO = searchPaginationRequestDTO.copy(
                    PageNo = nextPageNumber,
                    PageSize = Constants.PAGING.SEARCH_PAGE_SIZE * nextPageNumber
                )
            )
            return if (!response.succeeded) {
                error("No Records Found")
                LoadResult.Error(Throwable(message = "No Records Found"))
            } else {
                callback(response.Result.searchesResultItem.TotalRecordCount)


                LoadResult.Page(
                    data = response.Result.searchesResultItem.Items,
                    prevKey = null,
                    nextKey = if (response.Result.searchesResultItem.Items.size < Constants.PAGING.SEARCH_PAGE_SIZE) null else nextPageNumber.plus(
                        1
                    )
                )
            }


        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).

            error("No Records Found")
            LoadResult.Error(e)

        }
    }
}

//    SearchPaginationRequestDTO(
//    PageNo = nextPageNumber,
//    PageSize = Constants.PAGING.SEARCH_PAGE_SIZE * nextPageNumber,
//    Keyword = searchPaginationRequestDTO.Keyword ?: "",
//    Filter = searchPaginationRequestDTO.Filter ?: "",
//    Category = searchPaginationRequestDTO.Category ?: "",
//    Sort = searchPaginationRequestDTO.Sort ?: "",
//    IsOld = searchPaginationRequestDTO.IsOld ?: false,
//    Culture = searchPaginationRequestDTO.Culture ?: ""
//    )