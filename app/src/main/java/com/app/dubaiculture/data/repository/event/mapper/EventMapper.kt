package com.app.dubaiculture.data.repository.event.mapper

import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.request.EventDetailRequest
import com.app.dubaiculture.data.repository.event.remote.request.EventDetailRequestDTO
import com.app.dubaiculture.data.repository.event.remote.request.HomeEventListRequest
import com.app.dubaiculture.data.repository.event.remote.request.HomeEventListRequestDTO
import com.app.dubaiculture.data.repository.event.remote.response.HomeEventListingDTO
import com.app.dubaiculture.data.repository.event.remote.response.EventResponse


fun transformEventDetailRequest(eventDetailRequest: EventDetailRequest) =
    EventDetailRequestDTO(
        eventId = eventDetailRequest.eventId,
        culture = eventDetailRequest.culture
    )


fun transformHomeEventListingRequest(eventListRequest: HomeEventListRequest) =
    HomeEventListRequestDTO(
        culture = eventListRequest.culture
    )


fun transformHomeEventListing(eventResponse: EventResponse): ArrayList<EventHomeListing> =
    eventResponse.Result.homeEventListing.run {
        transformHomeEventListing(this)
    }

fun transformEventDetail(eventResponse: EventResponse):Events=
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
        type = eventResponse.Result.event.type
    )

fun transformHomeEventListing(homeEventList: ArrayList<HomeEventListingDTO>): ArrayList<EventHomeListing> =
    homeEventList.mapIndexed { index, it ->   {
        when(index){
            0->{
                EventHomeListing(
                    title = "Feature Events",
                    category = it.category,
                    events = it.events!!.map {
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
                            type = it.type
                        )
                    }
                )
            }
            else ->{
                EventHomeListing(
                    title = "More Events",
                    category = it.category,
                    events = it.events!!.map {
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
                            type = it.type
                        )
                    }
                )
            }
        }


    }} as ArrayList<EventHomeListing>