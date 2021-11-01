package com.app.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.trip.TripRepository
import com.app.dubaiculture.data.repository.trip.remote.request.SaveTripRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SaveTripViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository
) : BaseViewModel(application) {

    val tripName = ObservableField<String>()


    private var _saveTripStatus: MutableLiveData<Event<Boolean>> = MutableLiveData()
    var saveTripStatus: MutableLiveData<Event<Boolean>> = _saveTripStatus

    fun onSaveTripClicked(tripId: String) {

        if (tripName.get()!!.isNotEmpty()) {
            saveTrip(
                SaveTripRequest(
                    name = tripName.get()!!,
                    tripID = tripId
                )
            )
        } else
            return

    }

    private fun saveTrip(saveTripRequest: SaveTripRequest) {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.saveTrip(saveTripRequest)
            when (result) {
                is Result.Success -> {
                    showLoader(false)
//                    showErrorDialog(
//                        message = result.value.saveTripResponseDTO.message,
//                        colorBg = R.color.purple_900
//                    )
                    _saveTripStatus.value = Event(true)
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