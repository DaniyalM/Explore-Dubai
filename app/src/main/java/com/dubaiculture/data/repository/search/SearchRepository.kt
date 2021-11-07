package com.dubaiculture.data.repository.search

import androidx.paging.PagingData
import androidx.paging.map
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRepository
import com.dubaiculture.data.repository.search.local.SearchResultItem
import com.dubaiculture.data.repository.search.local.SearchTab
import com.dubaiculture.data.repository.search.mapper.transformHeader
import com.dubaiculture.data.repository.search.mapper.transformSearch
import com.dubaiculture.data.repository.search.mapper.transformSearchRequest
import com.dubaiculture.data.repository.search.remote.SearchRDS
import com.dubaiculture.data.repository.search.remote.request.SearchPaginationRequest
import com.dubaiculture.data.repository.search.remote.request.SearchRequest
import com.dubaiculture.data.repository.search.remote.request.SearchRequestDTO
import com.dubaiculture.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchRDS: SearchRDS,
) : BaseRepository() {

    suspend fun getSearchHeader(culture: String): Result<List<SearchTab>> {
        return when (val res = searchRDS.getSearchHeaders(SearchRequestDTO(Culture = culture))) {
            is Result.Success -> {
                if (res.value.succeeded) {
                    Result.Success(res.value.Result.headers.mapIndexed { index, searchHeaderDTO ->
                        transformHeader(searchHeaderDTO, index)
                    })
                } else {
                    Result.Failure(true, res.value.statusCode, null)
                }
            }
            is Result.Failure -> {
                Result.Failure(true, res.errorCode, null)
            }
            is Result.Error -> res
        }
    }

    suspend fun getSearchHistory(searchRequest: SearchRequest): Result<List<String>> {
        return when (val res = searchRDS.getSearchHistory(
            SearchRequestDTO(
                searchRequest.userId,
                searchRequest.culture
            )
        )) {
            is Result.Success -> {
                if (res.value.succeeded) {
                    Result.Success(res.value.Result.searches)
                } else {
                    Result.Failure(true, res.value.statusCode, null)
                }
            }
            is Result.Failure -> res
            is Result.Error -> res
        }

    }

    suspend fun clearHistory(searhRequest: SearchRequest): Result<Boolean> {
        return when (val res = searchRDS.clearSearchHistory(
            SearchRequestDTO(
                UserID = searhRequest.userId,
                Culture = searhRequest.culture
            )
        )) {

            is Result.Success -> {
                if (res.value.succeeded && res.value.Result.message.equals("Deleted")) {
                    Result.Success(true)
                } else {
                    Result.Failure(true, res.value.statusCode, null)
                }
            }
            is Result.Failure -> res
            is Result.Error -> res

        }
    }


    suspend fun fetchResults(
        searchPaginationRequest: SearchPaginationRequest,
        callback: (count: Int) -> Unit,
        error: (message: String) -> Unit,
    ): Result<Flow<PagingData<SearchResultItem>>> {
        val result =
            searchRDS.getSearchListing(
                transformSearchRequest(searchPaginationRequest),
                callback,
                error
            )
        return if (result is Result.Success) {
            Result.Success(result.value.map {
                it.map {
                    transformSearch(it)
                }
            })

        } else {
            Result.Failure(true, null, null, Constants.Error.SOMETHING_WENT_WRONG)
        }
    }


}