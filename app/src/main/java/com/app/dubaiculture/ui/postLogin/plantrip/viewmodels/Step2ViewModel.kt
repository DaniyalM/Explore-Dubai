package com.app.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.trip.TripRepository
import com.app.dubaiculture.data.repository.trip.local.InterestedIn
import com.app.dubaiculture.data.repository.trip.local.InterestedInType
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Step2ViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository
) : BaseViewModel(application) {

    private val _interestedIn: MutableLiveData<Event<InterestedIn>> = MutableLiveData()
    val interestedIn: LiveData<Event<InterestedIn>> = _interestedIn

    val _interestedInList: MutableLiveData<List<InterestedInType>> = MutableLiveData()
    val interestedInList: LiveData<List<InterestedInType>> = _interestedInList

    val _interestedIntype: MutableLiveData<InterestedInType> = MutableLiveData()
    val interestedIntype: LiveData<InterestedInType> = _interestedIntype

    init {
        getInterestedIn()
    }

    private fun getInterestedIn() {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.getInterestedIn()
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _interestedIn.value = result.value
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

    fun updateInterestedType(interestedType: InterestedInType) {
        _interestedIntype.value = interestedType
    }


    fun updateInInterestedInList(interestedInType: InterestedInType) {

        val data = _interestedInList.value ?: return

        data.map {
            if (interestedInType.id == it.id
            ) return@map interestedInType
            else return@map it
        }.let {
            _interestedInList.value = it
        }

    }

}