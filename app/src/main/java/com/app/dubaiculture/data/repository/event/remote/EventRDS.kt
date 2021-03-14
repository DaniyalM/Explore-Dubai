package com.app.dubaiculture.data.repository.event.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.event.remote.request.*
import com.app.dubaiculture.data.repository.event.service.EventService
import javax.inject.Inject

class EventRDS @Inject constructor(private val eventService: EventService) : BaseRDS() {

    suspend fun addItemtoFavorites(addToFavouriteRequestDTO: AddToFavouriteRequestDTO)=safeApiCall {
        eventService.addToFavourites(addToFavouriteRequestDTO)
    }
    suspend fun getEvent(homeEventListRequestDTO: HomeEventListRequestDTO) =
        safeApiCall {
            eventService.getEvents(homeEventListRequestDTO.culture)
        }

    suspend fun getEventsByFilter(eventFilterListRequestDTO: EventFiltersRequestDTO) =
        safeApiCall {
            eventService.getEventsWithFilters(eventFilterListRequestDTO)
        }


    suspend fun getEventDetail(eventDetailRequestDTO: EventDetailRequestDTO) =
        safeApiCall {
            eventService.getEventDetail(eventId = eventDetailRequestDTO.eventId,
                culture = eventDetailRequestDTO.culture)
        }

}