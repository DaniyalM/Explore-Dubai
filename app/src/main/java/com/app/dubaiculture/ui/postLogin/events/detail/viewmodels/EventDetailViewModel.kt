package com.app.dubaiculture.ui.postLogin.attractions.detail.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.event.EventRepository
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.request.EventRequest
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    application: Application,
    private val eventRepository: EventRepository,
    private val savedStateHandle: SavedStateHandle

) : BaseViewModel(application, eventRepository) {

    private val _eventDetail: MutableLiveData<Result<Events>> = MutableLiveData()
    val eventDetail: LiveData<Result<Events>> = _eventDetail
    private val context = getApplication<ApplicationEntry>()

    init {
        savedStateHandle.get<String>(Constants.NavBundles.EVENT_ID)?.let {
            getEventDetailsToScreen(it,context.auth.locale.toString())
        }

    }

    fun getEventDetailsToScreen(eventId: String, locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = eventRepository.fetchDetailEvent(
                EventRequest(
                    eventId = eventId,
                    culture = locale
                )
            )) {
                is Result.Success -> {
                    _eventDetail.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    _eventDetail.value = result
                    showLoader(false)
                }
            }
        }
    }

}