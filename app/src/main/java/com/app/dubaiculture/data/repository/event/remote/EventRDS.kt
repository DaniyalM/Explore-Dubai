package com.app.dubaiculture.data.repository.event.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.event.remote.request.*
import com.app.dubaiculture.data.repository.event.service.EventService
import javax.inject.Inject

class EventRDS @Inject constructor(private val eventService: EventService) : BaseRDS(eventService) {




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
            eventService.getEventDetail(eventId = eventDetailRequestDTO.eventId,
                culture = eventDetailRequestDTO.culture)

        }


}