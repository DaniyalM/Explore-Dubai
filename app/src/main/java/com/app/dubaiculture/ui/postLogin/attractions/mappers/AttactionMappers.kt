package com.app.dubaiculture.ui.postLogin.attractions.mappers

import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.local.models.Gallery
import com.app.dubaiculture.data.repository.attraction.local.models.SocialLink
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel

fun transformBaseToAttractionCategory(attractionCat: BaseModel) = AttractionCategory(
    id = attractionCat.id,
    icon = attractionCat.icon,
    title = attractionCat.title,
    selectedSvg = attractionCat.selectedSvg,
    color = attractionCat.color
)

fun transformBaseToAttraction(attraction: BaseModel) =
    Attractions(
        id = attraction.id.toString(),
        title = attraction.title.toString(),
        category = attraction.category.toString(),
        locationTitle = attraction.locationTitle,
        location = attraction.location,
        portraitImage = attraction.portraitImage,
        landscapeImage = attraction.landscapeImage,
        description = attraction.description,
        startTime = attraction.startTime,
            latitude = attraction.latitude,
            longitude = attraction.longitude,
        endTime = attraction.endTime,
        startDay = attraction.startDay,
        endDay = attraction.endDay,
        color = attraction.color,
        IsFavourite = attraction.isFavourite,
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
                    color=it.color,
                    dateTo = it.dateTo,
                    dateFrom = it.dateFrom,
                    locationTitle = it.locationTitle,
                    location = it.location?:"",
                    longitude = it.longitude?:"67.08119661055807",
                    latitude = it.latitude?:"24.83250180519734",
                    registrationDate = it.registrationDate,
                    isFavourite = it.isFavourite

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
        }
    )