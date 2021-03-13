package com.app.dubaiculture.data.repository.attraction.remote

import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionDetailRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequestDTO
import com.app.dubaiculture.data.repository.attraction.service.AttractionService
import com.app.dubaiculture.data.repository.base.BaseRDS
import javax.inject.Inject

class AttractionRDS @Inject constructor(private val attractionService: AttractionService) :
    BaseRDS() {
    suspend fun getAttractionCategories(attractionCategoryRequestDTO: AttractionCategoryRequestDTO) =
        safeApiCall {
            attractionService.getAttractionCategoryApi(attractionCategoryRequestDTO.culture)
        }

    suspend fun getAttractionDetail(attractionDetailRequestDTO: AttractionDetailRequestDTO) =
        safeApiCall {
            attractionService.getAttractionDetail(attractionId = attractionDetailRequestDTO.attractionId,
                culture = attractionDetailRequestDTO.culture)
        }

    suspend fun getAttractionsListingByCategory(attractionRequestDTO: AttractionRequestDTO) =
        safeApiCall {
            attractionService.getAttractionsListingByCategory(attractionCatId = attractionRequestDTO.attractionCategoryId,
                pageNumber = attractionRequestDTO.pageNumber,
                pageSize = attractionRequestDTO.pageSize,
                culture = attractionRequestDTO.culture)
        }
}