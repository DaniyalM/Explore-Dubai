package com.app.dubaiculture.data.repository.attraction.mapper

import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.local.models.Gallery
import com.app.dubaiculture.data.repository.attraction.local.models.SocialLink
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionDetailRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequest
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionResponse
import com.app.dubaiculture.data.repository.event.local.models.Events


fun transformAttractionsRequest(attractionRequest: AttractionRequest) =
    AttractionRequestDTO(
        attractionCategoryId = attractionRequest.attractionCategoryId!!,
        pageNumber = attractionRequest.pageNumber!!,
        pageSize = attractionRequest.pageSize!!,
        culture = attractionRequest.culture
    )

fun transformAttractionDetailRequest(attractionRequest: AttractionRequest) =
    AttractionDetailRequestDTO(
        attractionId = attractionRequest.attractionId!!,
        culture = attractionRequest.culture
    )


fun transformAttractionCategoryRequest(attractionRequest: AttractionRequest) =
    AttractionCategoryRequestDTO(
        culture = attractionRequest.culture
    )


fun transformAttractionCategory(attractionResponse: AttractionResponse): List<AttractionCategory> =
    attractionResponse.Result.attractionsCategories.run {
        transformAttractionCategory(this)
    }


fun transformAttractionCategory(list: List<AttractionCategoryDTO>): List<AttractionCategory> =
    list.run {
        this.map {
            AttractionCategory(
                id = it.id,
                icon = it.icon,
                title = it.title,
//                imgSelected = R.drawable.museums_icon_home,
//                imgUnSelected = R.drawable.museum,
                selectedSvg = it.selectedSvg,
                color = it.color
//                unselectedSvg = it.unselectedSvg,
            )
        }
    }

fun transformAttractionDetail(attractionResponse: AttractionResponse): Attractions =
    transformAttractionDetail(attractionResponse.Result.attraction)

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
    IsFavourite = attraction.isFavourite,
    events = attraction.events.map {
        Events(
            id = it.id,
            title = it.title,
            category = it.category,
            image = it.image,
            fromDate = it.fromDate,
            fromMonthYear = it.fromMonthYear,
            fromTime = it.fromTime,
            fromDay = it.fromDay,
            toDate = it.toDate,
            toMonthYear = it.toMonthYear,
            toTime = it.toTime,
            toDay = it.toDay,
            type = it.type,
//            color=it.color,
            dateTo = it.dateTo,
            dateFrom = it.dateFrom,
            locationTitle = it.locationTitle,
            location = it.location,
            longitude = it.longitude,
            latitude = it.latitude,
            isFavourite = it.isFavourite,
        )
    },
    gallery = attraction.gallery?.map {
        Gallery(
            isImage = it.isImage,
            galleryImage = it.galleryImage,
            galleryThumbnail = it.galleryThumbnail,
            galleryLink = it.galleryLink
        )
    }!!,
    socialLink = attraction.socialLinks?.map {
        SocialLink(
            facebookPageLink = it.facebookPageLink.toString(),
            facebookIcon = it.facebookIcon.toString(),
            instagramIcon = it.instagramIcon,
            instagramPageLink = it.instagramPageLink.toString()
        )
    }!!

)


fun transformAttractions(attractionResponse: AttractionResponse): List<Attractions> =
    attractionResponse.Result.attractions.run {
        transformAttractions(this)
    }

fun transformAttractions(list: List<AttractionDTO>): List<Attractions> =
    list.run {
        this.map {
            Attractions(
                id = it.id!!,
                title = it.title!!,
                category = it.category!!,
                IsFavourite = it.isFavourite,
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
        }
    }
