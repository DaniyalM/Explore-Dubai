package com.app.dubaiculture.ui.postLogin.events.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.EventRepository
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.app.dubaiculture.data.repository.event.remote.request.EventRequest
import com.app.dubaiculture.data.repository.event.remote.response.AddToFavouriteResponse
import com.app.dubaiculture.data.repository.event.remote.response.EventResponse
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.GpsStatusListener
import kotlinx.coroutines.launch

class EventViewModel @ViewModelInject constructor(
    application: Application,
    private val eventRepository: EventRepository,
) : BaseViewModel(application) {

    val gpsStatusLiveData = GpsStatusListener(application)
    private val _addToFavourite: MutableLiveData<Result<AddToFavouriteResponse>> = MutableLiveData()
    val addToFavourite: LiveData<Result<AddToFavouriteResponse>> = _addToFavourite
    private val _eventCategoryList: MutableLiveData<Result<EventHomeListing>> =
        MutableLiveData()
    val eventCategoryList: LiveData<Result<EventHomeListing>> =
        _eventCategoryList
    private val _eventDetailList: MutableLiveData<Result<Events>> = MutableLiveData()
    val eventDetail: LiveData<Result<Events>> = _eventDetailList

    private val _eventList: MutableLiveData<Result<List<Events>>> = MutableLiveData()
    val eventfilterRequest: LiveData<Result<List<Events>>> = _eventList


    private val _filterList: MutableLiveData<Result<EventResponse>> = MutableLiveData()
    val filterList: LiveData<Result<EventResponse>> = _filterList

    fun addToFavourite(userId: String, itemId: String, type: Int = 2) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = eventRepository.addToFavourite(AddToFavouriteRequest(
                userId = userId,
                itemId = itemId,
                type = type
            ))) {
                is Result.Success -> {
                    showLoader(false)
                    _addToFavourite.value = result
                }
                is Result.Failure -> {
                    showLoader(false)
                    _addToFavourite.value = result
                }

            }
        }
    }


    fun getEventHomeToScreen(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                eventRepository.fetchHomeEvents(EventRequest(culture = locale))) {
                is Result.Success -> {
                    _eventCategoryList.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    showLoader(false)
                    _eventCategoryList.value = result
                }
            }
        }
    }

    fun getDataFilterBtmSheet(locale: String){
        showLoader(true)
        viewModelScope.launch {
            when (val result = eventRepository.fetchDataFilterBtmSheet(EventRequest(culture = locale))){
                is Result.Success -> {
                    _filterList.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    showLoader(false)
                }
            }
        }
    }

    fun getEventDetailsToScreen(eventId: String, locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = eventRepository.fetchEvent(EventRequest(
                eventId = eventId,
                culture = locale))) {
                is Result.Success -> {
                    _eventDetailList.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    _eventDetailList.value = result
                    showLoader(false)
                }
            }
        }
    }

    fun getFilterEventList(eventRequest: EventRequest) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = eventRepository.fetchEventsbyFilters(eventRequest)) {
                is Result.Success -> {
                    _eventList.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    _eventList.value = result
                    showLoader(false)
                }
            }
        }
    }


     fun getMoreEvents(list:List<Events>) :List<Events> =
        list.filter {
            it.latitude == "" && it.longitude==""
        }

     fun getNearEvents(list:List<Events>) :List<Events> =
        list.filter {
            it.latitude != "" && it.longitude !=""
        }

}