package com.app.dubaiculture.data.repository.search.mapper

import com.app.dubaiculture.data.repository.search.local.SearchResultItem
import com.app.dubaiculture.data.repository.search.local.SearchResultItemDTO
import com.app.dubaiculture.data.repository.search.remote.request.SearchPaginationRequest
import com.app.dubaiculture.data.repository.search.remote.request.SearchPaginationRequestDTO

fun transformSearch(searchResultItemDTO: SearchResultItemDTO) = SearchResultItem(
    creationDate = searchResultItemDTO.CreationDate ?: "",
    title = searchResultItemDTO.Title ?: "",
    description = searchResultItemDTO.Description ?: "",
    image = searchResultItemDTO.Image ?: "",
    tags_DropLink = searchResultItemDTO.Tags_DropLink ?: mutableListOf(),
//    type = searchResultItemDTO.Type ?: "",
    type = "news",
    detailPageUrl = searchResultItemDTO.DetailPageUrl ?: "",
    isPage = searchResultItemDTO.isPage ?: false,
    itemDate = searchResultItemDTO.ItemDate ?: ""
)

fun transformSearchRequest(searchPaginationRequest: SearchPaginationRequest) =
    SearchPaginationRequestDTO(
        Keyword = searchPaginationRequest.keyword ?: "",
        Filter = searchPaginationRequest.filter ?: "",
        Category = searchPaginationRequest.category ?: "",
        Sort = searchPaginationRequest.sort ?: "",
        IsOld = searchPaginationRequest.isOld ?: false,
        Culture = searchPaginationRequest.culture ?: ""
    )