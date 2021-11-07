package com.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.trip.TripRepository
import com.dubaiculture.data.repository.trip.remote.response.DistanceMatrixResponse
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelModeViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository
) : BaseViewModel(application) {

    val _travelMode: MutableLiveData<String> = MutableLiveData(Constants.TRAVEL_MODE.DRIVING)
    val travelMode: LiveData<String> = _travelMode

    val _distanceResponse: MutableLiveData<DistanceMatrixResponse> = MutableLiveData()
    val distanceResponse: LiveData<DistanceMatrixResponse> = _distanceResponse

    fun getDistance(map:HashMap<String,String>) {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.getDistance(map)
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _distanceResponse.value = result.value
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