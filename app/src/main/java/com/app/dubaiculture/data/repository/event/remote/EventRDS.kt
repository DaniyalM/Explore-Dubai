package com.app.dubaiculture.data.repository.event.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.event.remote.request.HomeEventListRequestDTO
import com.app.dubaiculture.data.repository.event.service.EventService
import javax.inject.Inject

class EventRDS @Inject constructor(private val eventService: EventService) : BaseRDS() {

    suspend fun getEvent(homeEventListRequestDTO: HomeEventListRequestDTO) =
        safeApiCall {
            eventService.getEvents(homeEventListRequestDTO.culture)
        }

}