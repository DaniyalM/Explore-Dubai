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
import com.app.dubaiculture.data.repository.event.remote.request.EventDetailRequest
import com.app.dubaiculture.data.repository.event.remote.request.HomeEventListRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class EventViewModel @ViewModelInject constructor(
    application: Application,
    private val eventRepository: EventRepository,
) : BaseViewModel(application) {

    private val _eventCategoryList: MutableLiveData<Result<ArrayList<EventHomeListing>>> =
        MutableLiveData()
    val eventCategoryList: LiveData<Result<ArrayList<EventHomeListing>>> =
        _eventCategoryList

    private val _eventDetailList: MutableLiveData<Result<Events>> = MutableLiveData()
    val eventDetail: LiveData<Result<Events>> = _eventDetailList


    fun getEventHomeToScreen(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                eventRepository.fetchHomeEvents(HomeEventListRequest(culture = locale))) {
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
    fun getEventDetailsToScreen(eventId: String, locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = eventRepository.fetchEvent(EventDetailRequest(
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
}