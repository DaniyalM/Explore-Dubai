package com.app.dubaiculture.data.repository.attraction.mapper

import com.app.dubaiculture.data.repository.attraction.local.models.*
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionDetailRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequest
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionResponse
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.sitemap.local.BeaconItems


fun transformAttractionsRequest(attractionRequest: AttractionRequest) =
    AttractionRequestDTO(
        attractionCategoryId = attractionRequest.attractionCategoryId!!,

        culture = attractionRequest.culture
    )

fun transformVisitedAttractionsRequest(attractionRequest: AttractionRequest) =
    AttractionRequestDTO(culture = attractionRequest.culture)

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
    id = attraction.id,
    title = attraction.title,
    category = attraction.category,
    locationTitle = attraction.locationTitle,
    location = attraction.location,
    latitude = attraction.latitude,
    longitude = attraction.longitude,
    portraitImage = attraction.portraitImage,
    landscapeImage = attraction.landscapeImage,
    description = attraction.description,
    summary = attraction.summary,
    startTime = attraction.startTime,
    endTime = attraction.endTime,
    startDay = attraction.startDay,
    endDay = attraction.endDay,
    color = attraction.color,
    IsFavourite = attraction.isFavourite,
    emailContact = attraction.emailContact,
    numberContact = attraction.numberContact,
    siteMap = attraction.siteMapDTO?.let {
        SiteMap(
            image = it.image,
            siteMap = it.siteMapDTOS?.map {
                SiteMaps(
                    step = it.step,
                    title = it.title
                )
            }
        )
    },
    events = attraction.events?.let {
        it.map {
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
                location = it.location ?: "",
                longitude = it.longitude ?: "67.08119661055807",
                latitude = it.latitude ?: "24.83250180519734",
                isFavourite = it.isFavourite,
                registrationDate = it.registrationDate
            )
        }
    },
    gallery = attraction.gallery?.let {
        it.map {
            Gallery(
                isImage = it.isImage,
                galleryImage = it.galleryImage,
                galleryThumbnail = it.galleryThumbnail,
                galleryLink = it.galleryLink
            )
        }
    },
    socialLink = attraction.socialLinks?.let {
        it.map {
            SocialLink(
                facebookPageLink = it.facebookPageLink.toString(),
                facebookIcon = it.facebookIcon.toString(),
                instagramIcon = it.instagramIcon,
                instagramPageLink = it.instagramPageLink.toString()
            )
        }
    },
    asset360 = attraction.asset360?.let {
        Assets360(
            image = it.image,
            imageItems = it.imageItems?.map {
                ThreeSixtyImageItem(
                    xAxis = it.xAxis,
                    yAxis = it.yAxis,
                    title = it.title,
                    image = it.image
                )
            }
        )
    },
    ibecons = attraction.ibecon?.let {
        Ibecons(
            image = it.image,
            ibeconItems = it.iBeaconsItems?.map {
                BeaconItems(
                    step = it.step,
                    title = it.title,
                    image = it.img,
                    thumbnail = it.thumbnail,
                    summary = it.summary,
                    deviceID = it.deviceID,
                    visitedOn = it.visitedOn?:"",
                    visited = it.visited?:false
                )
            }
        )
    }

)


fun transformAttractions(attractionResponse: AttractionResponse): List<Attractions> =
    attractionResponse.Result.attractions.run {
        transformAttractions(this)
    }


fun transformAttractions(list: List<AttractionDTO>): List<Attractions> =
    list.run {
        this.map {
            Attractions(
                id = it.id,
                title = it.title,
                category = it.category,
                type = it.type,
                IsFavourite = it.isFavourite,
                locationTitle = it.locationTitle,
                location = it.location,
                portraitImage = it.portraitImage,
                longitude = it.longitude,
                latitude = it.latitude,
                landscapeImage = it.landscapeImage,
                description = it.description,
                startTime = it.startTime,
                endTime = it.endTime,
                endDay = it.endDay,
                startDay = it.startDay,
                color = it.color,
                visitedDateTime = it.visitedDateTime
            )
        }
    }


