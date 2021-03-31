package com.app.dubaiculture.data.repository.explore.mapper

import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.response.EventsDTO
import com.app.dubaiculture.data.repository.explore.local.models.AttractionsEvents
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
    fun transformationAttractionCategories(list :ArrayList <AttractionCategoryDTO> ) : ArrayList<AttractionCategory> {
       val attractionCatList = ArrayList<AttractionCategory>()
        list.run {
            attractionCatList.map {
                AttractionCategory(
                    id = it.id,
                    icon = it.icon,
                    title = it.title,
                    color = it.color,
                    attractions = it.attractions.let {
                        it?.map {
                            Attractions(
                                id = it.id,
                                title = it.title,
                                category = it.category,
                                locationTitle = it.locationTitle,
                                location = it.location,
                                portraitImage = it.portraitImage,
                                landscapeImage = it.landscapeImage,
                                description = it.description,
                                startTime = it.startTime,
                                endTime = it.endTime,
                                startDay = it.startDay,
                                endDay = it.endDay,
                                color = it.color,
                            )
                        }
                    } as ArrayList<Attractions>
                )
            }
        }
        return attractionCatList
    }


fun transformEvents(exploreResponse: ExploreResponse): ArrayList<Events> =

    exploreResponse.Result.events!!.run {
        transformationEvents(this)
    }

fun transformationEvents(eventsDTOList: ArrayList<EventsDTO>): ArrayList<Events>{
    val eventList  = ArrayList<Events>()
    eventsDTOList.run {
        eventList.map {
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
        }
    }

    return eventList


}


