package com.app.dubaiculture.data.repository.event

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.mapper.transformEventDetail
import com.app.dubaiculture.data.repository.event.mapper.transformEventDetailRequest
import com.app.dubaiculture.data.repository.event.mapper.transformHomeEventListing
import com.app.dubaiculture.data.repository.event.mapper.transformHomeEventListingRequest
import com.app.dubaiculture.data.repository.event.remote.EventRDS
import com.app.dubaiculture.data.repository.event.remote.request.EventDetailRequest
import com.app.dubaiculture.data.repository.event.remote.request.HomeEventListRequest
import javax.inject.Inject

class EventRepository @Inject constructor(private val eventRDS: EventRDS) : BaseRepository() {

    suspend fun fetchHomeEvents(homeEventListRequest: HomeEventListRequest): Result<List<EventHomeListing>> {
        return when (val resultRds =
            eventRDS.getEvent(transformHomeEventListingRequest(homeEventListRequest))) {

            is Result.Success -> {

                val listLds = resultRds
                if (listLds.value.statusCode != 200) {
                    Result.Failure(true, listLds.value.statusCode, null)
                } else {
                    val listRds = transformHomeEventListing(listLds.value)
                    Result.Success(listRds)
                }
            }

            is Result.Failure -> resultRds
            is Result.Error -> resultRds

        }

    }


    suspend fun fetchEvent(eventDetailRequest: EventDetailRequest): Result<Events> {
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
}