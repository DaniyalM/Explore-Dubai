package com.app.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.trip.TripRepository
import com.app.dubaiculture.data.repository.trip.local.LocationNearest
import com.app.dubaiculture.data.repository.trip.local.NearestLocation
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Step3ViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository
) : BaseViewModel(application) {

    val _type: MutableLiveData<LocationNearest> = MutableLiveData()
    val type: LiveData<LocationNearest> = _type


    val _usersType: MutableLiveData<List<LocationNearest>> = MutableLiveData()
    val usersType: LiveData<List<LocationNearest>> = _usersType

    private val _nearestLocation: MutableLiveData<NearestLocation> = MutableLiveData()
    val nearestLocation: LiveData<NearestLocation> = _nearestLocation

    init {
       // getNearestLocation()
    }


    private fun getNearestLocation() {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.getNearestLocation()
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _nearestLocation.value = result.value
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

    fun updateUserItem(userType: LocationNearest) {
        _type.value = userType
    }

    fun updateInUserTypeList(userType: LocationNearest) {

        val data = _usersType.value ?: return
        val updateData = updateToDefault(data)
        updateData.map {
            if (userType.locationId == it.locationId
            ) return@map userType
            else {
                return@map it
            }
        }.let {

            _usersType.value = it
        }

    }

    private fun updateToDefault(data: List<LocationNearest>): List<LocationNearest> {
        return data.map {
            it.copy(isChecked = false)
        }
    }

}