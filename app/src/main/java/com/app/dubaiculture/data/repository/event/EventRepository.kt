package com.app.dubaiculture.data.repository.event

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.event.local.models.EventFilterData
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.mapper.*
import com.app.dubaiculture.data.repository.event.remote.EventRDS
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.app.dubaiculture.data.repository.event.remote.request.EventRequest
import com.app.dubaiculture.data.repository.event.remote.response.AddToFavouriteResponse
import javax.inject.Inject

class EventRepository @Inject constructor(private val eventRDS: EventRDS) :
    BaseRepository(eventRDS) {

    suspend fun fetchHomeEvents(homeEventRequest: EventRequest): Result<EventHomeListing> {
        return when (val resultRds =
            eventRDS.getEvent(transformHomeEventListingRequest(homeEventRequest))) {
            is Result.Success -> {
                val listLds = resultRds
                if (listLds.value.statusCode != 200) {
                    Result.Failure(true, listLds.value.statusCode, null)
                } else {
//                    val listRDS=transformOtherEvents(listLds.value)
//                    val listRds = transformHomeEventListing(listLds.value)
                    Result.Success(EventHomeListing(
                        featureEvents = transformFeaturedEvents(listLds.value),
                        events = transformOtherEvents(listLds.value)
                    ))
                }
            }

            is Result.Failure -> resultRds
            is Result.Error -> resultRds

        }

    }

    suspend fun fetchEventsbyFilters(eventRequest: EventRequest): Result<List<Events>> {
        return when (val resultRds =
            eventRDS.getEventsByFilter(transformEventFiltersRequest(eventRequest))) {
            is Result.Success -> {
                val eventLDS = resultRds
                if (eventLDS.value.statusCode != 200) {
                    Result.Failure(true, eventLDS.value.statusCode, null)
                } else {
                    val eventRds = transformOtherEvents(eventLDS.value)
                    Result.Success(eventRds)
                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds
        }
    }

    suspend fun fetchEvent(eventDetailRequest: EventRequest): Result<Events> {
        return when (val resultRds =
            eventRDS.getEventDetail(transformEventDetailRequest(eventDetailRequest))) {
            is Result.Success -> {
                val eventLDS = resultRds
                if (eventLDS.value.statusCode != 200) {
                    Result.Failure(true, eventLDS.value.statusCode, null)
                } else {
                    val eventRds = transformEventDetail(eventLDS.value)
                    Result.Success(eventRds)
                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds
        }
    }

//    suspend fun addToFavourite(
//        addToFavouriteRequest: AddToFavouriteRequest,
//    ): Result<AddToFavouriteResponse> {
//        return when (val resultRds =
//            eventRDS.addToFavourates(transformAddToFavouriteRequest(addToFavouriteRequest))) {
//            is Result.Success -> {
//                if (resultRds.value.statusCode != 200) {
//                    Result.Failure(true, resultRds.value.statusCode, null)
//                } else {
//                    val eventRds = resultRds.value
//                    Result.Success(eventRds)
//                }
//            }
//            is Result.Failure -> resultRds
//            is Result.Error -> resultRds
//
//        }
//    }


    suspend fun fetchDataFilterBtmSheet(eventRequest: EventRequest): Result<EventFilterData> {
        return when (val resultRds =
            eventRDS.getDataFilterBottomSheet(transformHomeEventListingRequest(eventRequest))) {
            is Result.Success -> {
                val eventLDS = resultRds
                if (eventLDS.value.statusCode != 200) {
                    Result.Failure(true, eventLDS.value.statusCode, null)
                } else {
                    Result.Success(EventFilterData(
                        radioGroupList = transformationRadioList(eventLDS.value),
                        categoryList = transformationCategoryList(eventLDS.value),
                        locationList = transformationlocationList(eventLDS.value)
                    ))
                }
            }
            is Result.Failure -> {
                resultRds

            }
            is Result.Error -> {
                resultRds
            }

        }
    }

}