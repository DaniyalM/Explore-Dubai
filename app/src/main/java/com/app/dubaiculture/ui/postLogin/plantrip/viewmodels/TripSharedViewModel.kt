package com.app.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.trip.TripRepository
import com.app.dubaiculture.data.repository.trip.local.UserTypes
import com.app.dubaiculture.data.repository.trip.local.UsersType
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.Filter
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripSharedViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository
) : BaseViewModel(application) {

    val _showPlan: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val showPlan: LiveData<Event<Boolean>> = _showPlan

    private val context = getApplication<ApplicationEntry>()

    private val _userType: MutableLiveData<Event<UserTypes>> = MutableLiveData()
    val userType: LiveData<Event<UserTypes>> = _userType

    val _usersType: MutableLiveData<List<UsersType>> = MutableLiveData()
    val usersType: LiveData<List<UsersType>> = _usersType

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

    fun updateUserItem(userType: UsersType) {

        val data = _usersType.value ?: return
        data.map {
            if (userType.id == it.id
            ) return@map userType
            else return@map it
        }.let {
            _usersType.value = it
        }

    }


}