package com.app.dubaiculture.data.repository.search.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.search.local.SearchResultItemDTO
import com.app.dubaiculture.data.repository.search.remote.request.SearchPaginationRequestDTO
import com.app.dubaiculture.data.repository.search.remote.request.SearchRequestDTO
import com.app.dubaiculture.data.repository.search.remote.service.SearchService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRDS @Inject constructor(private val searchService: SearchService) : BaseRDS() {

    suspend fun getSearchHeaders(searchRequest: SearchRequestDTO) = safeApiCall {
        searchService.getSearchHeaders(
            culture = searchRequest.Culture
        )
    }

    suspend fun getSearchHistory(searchRequest: SearchRequestDTO) =
        safeApiCall {
            searchService.getSearchHistory(
                searchRequest.Culture,
                searchRequest.UserID
            )
        }

    suspend fun clearSearchHistory(searchRequest: SearchRequestDTO) =
        safeApiCall {
            searchService.clearHistory(
                searchRequest
            )
        }


    suspend fun getSearchListing(
        searchPaginationRequestDTO: SearchPaginationRequestDTO,
        callback: (count: Int) -> Unit,
        error: (message: String) -> Unit

    ): Result<Flow<PagingData<SearchResultItemDTO>>> {
        return safeApiCall {
            Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = {
                    SearchPagingSource(
                        searchService,
                        searchPaginationRequestDTO,
                        callback = callback,
                        error = error

                    )
                }
            ).flow
        }

    }
}