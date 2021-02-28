package com.app.dubaiculture.data.repository.attraction.mapper

import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequest
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryResponse
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequest
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequestDTO
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreDTO
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreResponse

fun transformAttractionCategoryRequest(attractionCategoryRequest: AttractionCategoryRequest) = AttractionCategoryRequestDTO(
    culture = attractionCategoryRequest.culture
)


fun transformAttractionCategory(attractionCatResponse: AttractionCategoryResponse): List<AttractionCategory> =
    attractionCatResponse.Result.attractionsCategories.run {
       transformAttractionCategory(this!!)
    }

fun transformAttractionCategory(list: List<AttractionCategoryDTO>): List<AttractionCategory> = list.run {

    this.map{
        AttractionCategory(
            id = it.id,
            icon = it.icon,
            title = it.title,
            selectedSvg = it.selectedSvg,
            unselectedSvg = it.unselectedSvg
        )
    }

}
