package com.dubaiculture.data.repository.event

import android.app.usage.UsageEvents
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRepository
import com.dubaiculture.data.repository.event.local.models.EventFilterData
import com.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.data.repository.event.local.models.schedule.Schedule
import com.dubaiculture.data.repository.event.mapper.*
import com.dubaiculture.data.repository.event.remote.EventRDS
import com.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.dubaiculture.data.repository.event.remote.request.EventRequest
import com.dubaiculture.data.repository.event.remote.response.AddToFavouriteResponse
import com.dubaiculture.data.repository.event.remote.response.EventResponse
import com.dubaiculture.data.repository.event.remote.response.ScheduleResponse
import okhttp3.MultipartBody
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

    suspend fun fetchDetailEvent(eventRequest: EventRequest): Result<Events> {
        return when (val resultRds =
                eventRDS.getEventDetail(transformEventDetailRequest(eventRequest))) {
            is Result.Success -> {
                val eventLDS = resultRds
                if (eventLDS.value.statusCode != 200) {
                    Result.Failure(true, eventLDS.value.statusCode, null)
                } else {
                    Result.Success(transformEventDetail(eventLDS.value))
                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds
        }
    }

    suspend fun fetchMyEvent(locale : String):Result<List<Events>>{
        return when(val resultRds = eventRDS.getMyEvent(locale)){
            is Result.Success ->{
                val eventLDS = resultRds
                if(eventLDS.value.statusCode!=200){
                    Result.Failure(true, eventLDS.value.statusCode, null)
                }else{
                    Result.Success(transformEventList(resultRds.value.Result.otherEvents))
                }
            }
            is Result.Failure->{
                resultRds
            }
            is Result.Error ->{
                resultRds
            }
        }
    }

//    suspend fun addToFavourite(addToFavouriteRequest: AddToFavouriteRequest): Result<AddToFavouriteResponse> {
//        return when (val resultRds =
//            eventRDS.addItemtoFavorites(transformAddToFavouriteRequest(addToFavouriteRequest))) {
//            is Result.Success -> {
//                val eventLDS = resultRds
//                if (eventLDS.value.statusCode != 200) {
//                    Result.Failure(true, eventLDS.value.statusCode, null)
//                } else {
//
//                    val eventRds = eventLDS.value
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


    suspend fun submitRegister(
            eventId: String,
            slotId: String,
            occupation: String,
            culture : String,
            file: MultipartBody.Part? = null
    ): Result<EventResponse> {
        val response = eventRDS.getEventRegister(
                eventId.trim(),
                slotId.trim(),
                occupation.trim(),
                culture.trim(),
                file
        )
        return if (response is Result.Success){
            if (response.value.succeeded) {
                Result.Success(response.value)
            } else {
                if (response.value.errorMessage == "") {
                    Result.Failure(false, null, null, "asd" + " Please try again")
                } else {
                    Result.Failure(false, null, null, response.value.errorMessage)
                }
            }
        } else {
           Result.Failure(true, null, null, "Error")
        }

    }
}