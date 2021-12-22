package com.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.trip.TripRepository
import com.dubaiculture.data.repository.trip.local.Durations
import com.dubaiculture.data.repository.trip.local.EventAttractions
import com.dubaiculture.data.repository.trip.remote.request.EventAttractionRequest
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Step4ViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository
) : BaseViewModel(application) {
    private val context = getApplication<ApplicationEntry>()

     val _durations: MutableLiveData<Durations> = MutableLiveData()
    val durations: LiveData<Durations> = _durations

     val _eventAttraction: MutableLiveData<Event<EventAttractions>> = MutableLiveData()
    val eventAttraction: LiveData<Event<EventAttractions>> = _eventAttraction

    init {
        getDurations()
    }

    fun getDurations() {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.getDurations(context.auth.locale.toString())
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _durations.value = result.value
                }
                is Result.Error -> {
                    showLoader(false)
                    showToast(Constants.Error.SOMETHING_WENT_WRONG)
                }
                is Result.Failure -> {
                    showLoader(false)
                    showToast(Constants.Error.SOMETHING_WENT_WRONG)
                }
            }
        }


    }


    fun postEventAttraction(eventAttractionRequest: EventAttractionRequest) {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.postEventAttraction(eventAttractionRequest)
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _eventAttraction.value = Event(result.value)
                }
                is Result.Error -> {
                    showLoader(false)
                    showToast(Constants.Error.SOMETHING_WENT_WRONG)
                }
                is Result.Failure -> {
                    showLoader(false)
                    showToast(Constants.Error.SOMETHING_WENT_WRONG)
                }
            }
        }

    }

}