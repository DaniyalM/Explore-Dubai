package com.app.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.trip.TripRepository
import com.app.dubaiculture.data.repository.trip.local.UserTypes
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants.Error.SOMETHING_WENT_WRONG
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Step1ViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository
) : BaseViewModel(application) {
    private val context = getApplication<ApplicationEntry>()

    private val _userType: MutableLiveData<Event<UserTypes>> = MutableLiveData()
    val userType: LiveData<Event<UserTypes>> = _userType

    init {
        getUserType()
    }

    fun getUserType() {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.getUserType()
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _userType.value = result.value
                }
                is Result.Error -> {
                    showLoader(false)
                    showToast(SOMETHING_WENT_WRONG)
//                     result.exception
                }
                is Result.Failure -> {
                    showLoader(false)
                    showToast(SOMETHING_WENT_WRONG)

//                    _userType.value = result
                }
            }
        }

    }

}