package com.app.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequest
import com.app.dubaiculture.data.repository.trip.TripRepository
import com.app.dubaiculture.data.repository.trip.local.EventAttractions
import com.app.dubaiculture.data.repository.trip.local.Trip
import com.app.dubaiculture.data.repository.trip.remote.request.EventAttractionRequest
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveTripListingViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository
) : BaseViewModel(application) {

    private val _tripPagination: MutableLiveData<PagingData<Trip>> = MutableLiveData()
    val tripPagination: LiveData<PagingData<Trip>> = _tripPagination
    private val context = getApplication<ApplicationEntry>()

    private val _eventAttraction: MutableLiveData<Event<EventAttractions>> = MutableLiveData()
    val eventAttraction: LiveData<Event<EventAttractions>> = _eventAttraction

    init {
        getTrips()
    }

    private fun getTrips() {
        viewModelScope.launch {
            when (val result = tripRepository.getMyTrips(
                context.auth.locale.toString()
            )) {
                is Result.Success -> {
                    result.value
                        .cachedIn(viewModelScope)
                        .collectLatest {
                            _tripPagination.value = it
                        }
                }
                is Result.Failure -> {
                    showLoader(false)
                }
            }
        }
    }

    fun filterTrips(tripId: String) {
        val data = _tripPagination.value ?: return
        data.filter { it.tripId != tripId }.let { _tripPagination.value = it }
    }


    fun getTripDetails(tripId: String, culture: String) {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.getTripDetails(tripId, culture)
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
