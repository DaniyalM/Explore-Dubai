package com.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.trip.TripRepository
import com.dubaiculture.data.repository.trip.local.DistanceDirectionModel
import com.dubaiculture.data.repository.trip.remote.response.DirectionResponse
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

    val _directionDistanceResponse: MutableLiveData<DistanceDirectionModel> = MutableLiveData()
    val directionDistanceResponse: LiveData<DistanceDirectionModel> = _directionDistanceResponse

    val _directionResponse: MutableLiveData<DirectionResponse> = MutableLiveData()
    val directionResponse: LiveData<DirectionResponse> = _directionResponse

    fun getDistance(map: HashMap<String, String>) {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.getDistance(map)
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _distanceResponse.value = result.value
                    getDirections(map)
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

    private fun getDirections(map: HashMap<String, String>) {
        val hashMap: HashMap<String, String> = HashMap<String, String>()

        val originLocation = map["origins"]?.split(",")
        val destinationLocation = map["destinations"]?.split(",")

        hashMap["origin"] =
            originLocation?.get(0).toString() + "," + originLocation?.get(1).toString()

        hashMap["destination"] =
            destinationLocation?.get(0).toString() + "," + destinationLocation?.get(1).toString()
        hashMap["mode"] = map["mode"].toString()

        hashMap["key"] = map["key"].toString()
        hashMap["language"] = map["language"].toString()
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.getDirections(hashMap)
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _directionResponse.value = result.value
                    _directionDistanceResponse.value = DistanceDirectionModel(
                        _directionResponse.value ?: return@launch,
                        _distanceResponse.value ?: return@launch
                    )
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