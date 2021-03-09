package com.app.dubaiculture.data.repository.event.mapper

import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.remote.request.HomeEventListRequest
import com.app.dubaiculture.data.repository.event.remote.request.HomeEventListRequestDTO
import com.app.dubaiculture.data.repository.event.remote.response.HomeEventListingDTO
import com.app.dubaiculture.data.repository.event.remote.response.HomeEventResponse

fun transformHomeEventListingRequest(eventListRequest: HomeEventListRequest) =
    HomeEventListRequestDTO(
        culture = eventListRequest.culture
    )


fun transformHomeEventListing(homeEventResponse: HomeEventResponse): ArrayList<EventHomeListing> =
    homeEventResponse.Result.homeEventListing.run {
        transformHomeEventListing(this)
    }

fun transformHomeEventListing(homeEventList:ArrayList<HomeEventListingDTO>):ArrayList<EventHomeListing> =
        homeEventList.map {
            EventHomeListing(
                category = it.category,
                events = it.events!!
            )
        } as ArrayList<EventHomeListing>