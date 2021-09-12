package com.app.dubaiculture.data.repository.event.remote

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequestDTO
import com.app.dubaiculture.data.repository.event.remote.request.EventDetailRequestDTO
import com.app.dubaiculture.data.repository.event.remote.request.EventFiltersRequestDTO
import com.app.dubaiculture.data.repository.event.remote.request.HomeEventListRequestDTO
import com.app.dubaiculture.data.repository.event.remote.response.EventResponse
import com.app.dubaiculture.data.repository.event.service.EventService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class EventRDS @Inject constructor(private val eventService: EventService) : BaseRDS(eventService) {

//    suspend fun addToFavourates(addToFavouriteRequestDTO: AddToFavouriteRequestDTO) =
//        safeApiCall {
//            eventService.addToFavourites(addToFavouriteRequestDTO)
//        }


    suspend fun getEvent(homeEventListRequestDTO: HomeEventListRequestDTO) =
        safeApiCall {
            eventService.getEvents(homeEventListRequestDTO.culture)
        }


    // event listing
    suspend fun getEventsByFilter(eventFilterListRequestDTO: EventFiltersRequestDTO) =
        safeApiCall {
            eventService.getEventsWithFilters(eventFilterListRequestDTO)
        }

    // event bottom sheet filter
    suspend fun getDataFilterBottomSheet(homeEventListRequestDTO: HomeEventListRequestDTO) =
        safeApiCall {
            eventService.getEventFilters(homeEventListRequestDTO.culture)
        }

    suspend fun getEventDetail(eventDetailRequestDTO: EventDetailRequestDTO) =
        safeApiCall {
            eventService.getEventDetail(eventId = eventDetailRequestDTO.id,
                culture = eventDetailRequestDTO.culture)

        }


    suspend fun getMyEvent(locale : String) =
        safeApiCall {
            eventService.getMyEvent(locale)

        }


    suspend fun getEventRegister(
            eventId: String,
            slotId: String,
            occupation: String,
            file: MultipartBody.Part? =null,
    ): Result<EventResponse> {
        return safeApiCall {
            eventService.getEventRegister(
                    eventId.toRequestBody("text/plain".toMediaType()),
                    slotId.toRequestBody("text/plain".toMediaType()),
                    occupation.toRequestBody("text/plain".toMediaType()),
                    file
            )
        }
    }
}
//
//eventId.toRequestBody("text/plain".toMediaType()),
//slotId.toRequestBody("text/plain".toMediaType()),
//occupation.toRequestBody("text/plain".toMediaType()),