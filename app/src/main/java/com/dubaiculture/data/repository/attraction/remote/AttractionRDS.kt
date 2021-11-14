package com.dubaiculture.data.repository.attraction.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequestDTO
import com.dubaiculture.data.repository.attraction.remote.request.AttractionDetailRequestDTO
import com.dubaiculture.data.repository.attraction.remote.request.AttractionRequestDTO
import com.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.dubaiculture.data.repository.attraction.remote.response.AttractionResponse
import com.dubaiculture.data.repository.attraction.service.AttractionService
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequestDTO
import com.dubaiculture.data.repository.news.remote.NewsPagingSource
import com.dubaiculture.data.repository.news.remote.request.LatestNewsDTO
import com.dubaiculture.data.repository.news.remote.request.NewsRequestDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AttractionRDS @Inject constructor(private val attractionService: AttractionService) :
    BaseRDS(attractionService) {

    suspend fun getAttractionCategories(attractionCategoryRequestDTO: AttractionCategoryRequestDTO) =
        safeApiCall {
            attractionService.getAttractionCategoryApi(attractionCategoryRequestDTO.culture)
        }

    suspend fun getAttractionDetail(attractionDetailRequestDTO: AttractionDetailRequestDTO) =
        safeApiCall {
            attractionService.getAttractionDetail(attractionId = attractionDetailRequestDTO.attractionId,
                culture = attractionDetailRequestDTO.culture)
        }
    suspend fun getAttractionDetailForThreeSixty(attractionDetailRequestDTO: AttractionDetailRequestDTO) =
        safeApiCall {
            attractionService.getAttractionDetailForThreeSixty(attractionId = attractionDetailRequestDTO.attractionId,
                culture = attractionDetailRequestDTO.culture)
        }

//    suspend fun getAttractionsListingByCategory(attractionRequestDTO: AttractionRequestDTO) =
//        safeApiCall {
//            attractionService.getAttractionsListingByCategory(attractionCatId = attractionRequestDTO.attractionCategoryId!!,
//                pageNumber = attractionRequestDTO.pageNumber!!,
//                pageSize = attractionRequestDTO.pageSize!!,
//                culture = attractionRequestDTO.culture)
//        }

    suspend fun getVisitedPlaces(attractionRequestDTO: AttractionRequestDTO)= safeApiCall {
        attractionService.getVisitedPlaces(attractionRequestDTO.culture
        )
    }

    suspend fun getAttractionsListingByCategory(attractionRequestDTO: AttractionRequestDTO): Result<Flow<PagingData<AttractionDTO>>> {
        return safeApiCall {
            Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { AttractionPagingSource(attractionService,attractionRequestDTO) }
            ).flow
        }

    }

}