package com.app.dubaiculture.data.repository.explore.mapper

import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryDTO
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.response.EventsDTO
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequest
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequestDTO
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreDTO
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreResponse


fun transformExploreRequest(exploreRequest: ExploreRequest) = ExploreRequestDTO(
    culture = exploreRequest.culture
)

fun transformExplore(exploreResponse: ExploreResponse): List<Explore> =
    exploreResponse.Result.value.run {
        this.let { transformExplore(it) }

    }

fun transformExplore(list: List<ExploreDTO>): List<Explore> = list.run {
    this.map {
        Explore(
            title = it.title,
            category = it.category,
            value = it.value
        )
    }
}


fun transformAttractionCategories(exploreResponse: ExploreResponse): ArrayList<AttractionCategory> =
    exploreResponse.Result.attractionsCategories!!.run {
        transformationAttractionCategories(this)
    }

fun transformationAttractionCategories(list: ArrayList<AttractionCategoryDTO>): ArrayList<AttractionCategory> =
    list.mapIndexed { index, attractionCategoryDTO ->
        AttractionCategory(
            id = attractionCategoryDTO.id,
            icon = attractionCategoryDTO.icon,
            selectedSvg = attractionCategoryDTO.selectedSvg,
            title = attractionCategoryDTO.title,
            color = attractionCategoryDTO.color,
            indexId = index,
            attractions = attractionCategoryDTO.attractions.map { attraction ->
                Attractions(
                    id = attraction.id,
                    title = attraction.title,
                    category = attraction.category,
                    locationTitle = attraction.locationTitle,
                    location = attraction.location,
                    latitude = attraction.latitude ?: "24.83250180519734",
                    longitude = attraction.longitude ?: "67.08119661055807",
                    portraitImage = attraction.portraitImage,
                    landscapeImage = attraction.landscapeImage,
                    description = attraction.description,
                    startTime = attraction.startTime,
                    endTime = attraction.endTime,
                    startDay = attraction.startDay,
                    endDay = attraction.endDay,
                    color = attraction.color,
                    withinRadiusIcon = attraction.mapIconColored,
                    outOfRadiusIcon = attraction.mapIconGreyscale,
                    catIndexId = index
                )

            } as ArrayList<Attractions>
        )
    } as ArrayList<AttractionCategory>


fun transformEvents(exploreResponse: ExploreResponse): ArrayList<Events> =

    exploreResponse.Result.events!!.run {
        transformationEvents(this)
    }

fun transformationEvents(eventsDTOList: ArrayList<EventsDTO>): ArrayList<Events> =
    eventsDTOList.map {
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
            longitude = it.longitude?:"67.08119661055807",
            latitude = it.latitude?:"24.83250180519734",
            isFavourite = it.isFavourite,
            registrationDate = it.registrationDate
        )
    } as ArrayList<Events>




