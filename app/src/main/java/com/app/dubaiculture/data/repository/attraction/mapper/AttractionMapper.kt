package com.app.dubaiculture.data.repository.attraction.mapper

import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequest
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionDetailRequest
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionDetailRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryResponse
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDetailResponse


fun transformAttractionDetailRequest(attractionDetailRequest: AttractionDetailRequest) =
    AttractionDetailRequestDTO(
        attractionId = attractionDetailRequest.attractionId,
        culture = attractionDetailRequest.culture
    )


fun transformAttractionCategoryRequest(attractionCategoryRequest: AttractionCategoryRequest) =
    AttractionCategoryRequestDTO(
        culture = attractionCategoryRequest.culture
    )


fun transformAttractionCategory(attractionCatResponse: AttractionCategoryResponse): ArrayList<AttractionCategory> =
    attractionCatResponse.Result.attractionsCategories.run {
        transformAttractionCategory(this)
    }

fun transformAttractionCategory(list: ArrayList<AttractionCategoryDTO>): ArrayList<AttractionCategory> =
    list.run {
        this.map {
            AttractionCategory(
                id = it.id,
                icon = it.icon,
                title = it.title,
                imgSelected = R.drawable.museums_icon_home,
                imgUnSelected = R.drawable.museum,
                selectedSvg = it.selectedSvg,
                unselectedSvg = it.unselectedSvg,
                attractions = it.attractions.map {
                    Attractions(
                        id = it.id!!,
                        title = it.title!!,
                        category = it.category!!,
                        IsFavourite = it.isFavourite!!,
                        locationTitle = it.locationTitle,
                        location = it.location,
                        portraitImage = it.portraitImage,
                        landscapeImage = it.landscapeImage,
                        description = it.description,
                        startTime = it.startTime,
                        endTime = it.endTime,
                        endDay = it.endDay,
                        startDay = it.startDay,
                        color = it.color

                    )
                } as ArrayList<Attractions>
            )
        }
    } as ArrayList<AttractionCategory>

fun transformAttractionDetail(attractionDetailResponse: AttractionDetailResponse): Attractions =
    transformAttractionDetail(attractionDetailResponse.Result.attraction)

fun transformAttractionDetail(attraction: AttractionDTO): Attractions = Attractions(
    id = attraction.id!!,
    title = attraction.title!!,
    category = attraction.category!!,
    locationTitle = attraction.locationTitle!!,
    location = attraction.location,
    portraitImage = attraction.portraitImage,
    landscapeImage = attraction.landscapeImage,
    description = attraction.description,
    startTime = attraction.startTime,
    endTime = attraction.endTime,
    startDay = attraction.startDay,
    endDay = attraction.endDay,
    color = attraction.color,
    IsFavourite = attraction.isFavourite!!,
    events = attraction.events!!
)
