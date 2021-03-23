package com.app.dubaiculture.data.repository.event.mapper

import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.local.models.Filter
import com.app.dubaiculture.data.repository.event.remote.request.*
import com.app.dubaiculture.data.repository.event.remote.response.EventResponse
import com.app.dubaiculture.data.repository.event.remote.response.EventsDTO
import com.app.dubaiculture.data.repository.event.remote.response.FilterDTO

// Add To Favourite Request is used for both Attractions and events from below
fun transformAddToFavouriteRequest(addToFavouriteRequest: AddToFavouriteRequest) =
    AddToFavouriteRequestDTO(
        UserID = addToFavouriteRequest.userId,
        ItemID = addToFavouriteRequest.itemId,
        Type = addToFavouriteRequest.type!!
    )

// Add To Favourite Request is used for both Attractions and events from above
fun transformEventFiltersRequest(eventRequest: EventRequest) =
    EventFiltersRequestDTO(
        Culture = eventRequest.culture!!,
        UserID = eventRequest.userId,
        Category = eventRequest.category,
        Keyword = eventRequest.keyword,
        DateFrom = eventRequest.dateFrom,
        DateTo = eventRequest.dateTo,
        Type = eventRequest.type,
        Location = eventRequest.location

    )

fun transformEventDetailRequest(eventDetailRequest: EventRequest) =
    EventDetailRequestDTO(
        eventId = eventDetailRequest.eventId!!,
        culture = eventDetailRequest.culture!!
    )


fun transformOtherEvents(eventResponse: EventResponse): ArrayList<Events> =
    eventResponse.Result.otherEvents.run {
        transformOtherEventList(this)
    }


fun transformationRadioList(eventResponse: EventResponse):ArrayList<Filter> =
    eventResponse.Result.radioGroupList.run {
        transformRadioBtnList(this)
    }

fun transformRadioBtnList(filter : ArrayList<FilterDTO>): ArrayList<Filter> =
    filter.map {
        Filter(
            id = it.id,
            title = it.title
        )
    } as ArrayList<Filter>


fun transformationCategoryList(eventResponse: EventResponse):ArrayList<Filter> =
    eventResponse.Result.categoryList.run {
        transformcategoryList(this)
    }

fun transformcategoryList(filter : ArrayList<FilterDTO>): ArrayList<Filter> =
    filter.map {
        Filter(
            id = it.id,
            title = it.title
        )
    } as ArrayList<Filter>


fun transformationlocationList(eventResponse: EventResponse):ArrayList<Filter> =
    eventResponse.Result.locationList.run {
        transformlocationList(this)
    }
fun transformlocationList(filter : ArrayList<FilterDTO>): ArrayList<Filter> =
    filter.map {
        Filter(
            id = it.id,
            title = it.title
        )
    } as ArrayList<Filter>




fun transformOtherEventList(events: ArrayList<EventsDTO>): ArrayList<Events> =
    events.map {
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
    } as ArrayList<Events>


fun transformFeaturedEvents(eventResponse: EventResponse): ArrayList<Events> =
    eventResponse.Result.featuredEvents.run {
        transformFeatureEventList(this)
    }

fun transformFeatureEventList(featureEvents: ArrayList<EventsDTO>): ArrayList<Events> =
    featureEvents.map {
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
            color=it.color?:"",
            dateTo = it.dateTo,
            dateFrom = it.dateFrom,
            locationTitle = it.locationTitle,
            location = it.location,
            longitude = it.longitude,
            latitude = it.latitude,
            isFavourite = it.isFavourite,
        )
    } as ArrayList<Events>


fun transformHomeEventListingRequest(eventListRequest: EventRequest) =
    HomeEventListRequestDTO(
        culture = eventListRequest.culture!!
    )


//fun transformHomeEventListing(eventResponse: EventResponse): ArrayList<EventHomeListing> =
//    eventResponse.Result.homeEventListing.run {
//        transformHomeEventListing(this)
//    }

fun transformEventDetail(eventResponse: EventResponse): Events =
    Events(
        id = eventResponse.Result.event.id,
        title = eventResponse.Result.event.title,
        category = eventResponse.Result.event.category,
        image = eventResponse.Result.event.image,
        fromDate = eventResponse.Result.event.fromDate,
        fromMonthYear = eventResponse.Result.event.fromMonthYear,
        fromTime = eventResponse.Result.event.fromTime,
        fromDay = eventResponse.Result.event.fromDay,
        toDate = eventResponse.Result.event.toDate,
        toMonthYear = eventResponse.Result.event.toMonthYear,
        toTime = eventResponse.Result.event.toTime,
        toDay = eventResponse.Result.event.toDay,
        type = eventResponse.Result.event.type,
        color=eventResponse.Result.event.color?:"",
        dateTo = eventResponse.Result.event.dateTo,
        dateFrom = eventResponse.Result.event.dateFrom,
        locationTitle =  eventResponse.Result.event.locationTitle,
        location =  eventResponse.Result.event.location,
        longitude =  eventResponse.Result.event.longitude,
        latitude =  eventResponse.Result.event.latitude,
        isFavourite = eventResponse.Result.event.isFavourite,

        )
//
//fun transformHomeEventListing(homeEventList: ArrayList<HomeEventListingDTO>): ArrayList<EventHomeListing> =
//    homeEventList.mapIndexed { index, it ->
//        {
//            when (index) {
//                0 -> {
//                    EventHomeListing(
//                        title = "Feature Events",
//                        category = it.category,
//                        events = it.events!!.map {
//                            Events(
//                                id = it.id,
//                                title = it.title,
//                                category = it.category,
//                                image = it.image,
//                                fromDate = it.fromDate,
//                                fromMonthYear = it.fromMonthYear,
//                                fromTime = it.fromTime,
//                                fromDay = it.fromDay,
//                                toDate = it.toDate,
//                                toMonthYear = it.toMonthYear,
//                                toTime = it.toTime,
//                                toDay = it.toDay,
//                                type = it.type
//                            )
//                        }
//                    )
//                }
//                else -> {
//                    EventHomeListing(
//                        title = "More Events",
//                        category = it.category,
//                        events = it.events!!.map {
//                            Events(
//                                id = it.id,
//                                title = it.title,
//                                category = it.category,
//                                image = it.image,
//                                fromDate = it.fromDate,
//                                fromMonthYear = it.fromMonthYear,
//                                fromTime = it.fromTime,
//                                fromDay = it.fromDay,
//                                toDate = it.toDate,
//                                toMonthYear = it.toMonthYear,
//                                toTime = it.toTime,
//                                toDay = it.toDay,
//                                type = it.type
//                            )
//                        }
//                    )
//                }
//            }
//
//
//        }
//    } as ArrayList<EventHomeListing>