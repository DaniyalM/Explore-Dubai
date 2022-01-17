package com.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.trip.TripRepository
import com.dubaiculture.data.repository.trip.local.MyTripCount
import com.dubaiculture.data.repository.trip.mapper.transformMyTripCount
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class PlanYourTripViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository
) : BaseViewModel(application) {
    private val context = getApplication<ApplicationEntry>()

    private val _tripCount: MutableLiveData<Event<Int>> = MutableLiveData()
    val tripCount: LiveData<Event<Int>> = _tripCount

//    init {
//        getTripCount()
//    }

     fun getTripCount() {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.getTripCount(context.auth.locale.toString())
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _tripCount.value = Event(result.value.count.Count)
                }
                is Result.Error -> {
                    showLoader(false)
                    showToast(Constants.Error.SOMETHING_WENT_WRONG)
//                     result.exception
                }
                is Result.Failure -> {
                    showLoader(false)
                    showToast(Constants.Error.SOMETHING_WENT_WRONG)

//                    _userType.value = result
                }
            }
        }
    }

}