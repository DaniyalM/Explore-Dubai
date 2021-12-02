package com.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.trip.TripRepository
import com.dubaiculture.data.repository.trip.local.UserTypes
import com.dubaiculture.data.repository.trip.local.UsersType
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants.Error.SOMETHING_WENT_WRONG
import com.dubaiculture.utils.event.Event
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

    val _type: MutableLiveData<UsersType> = MutableLiveData()
    val type: LiveData<UsersType> = _type


    val _usersType: MutableLiveData<List<UsersType>> = MutableLiveData()
    val usersType: LiveData<List<UsersType>> = _usersType

    private val _userType: MutableLiveData<Event<UserTypes>> = MutableLiveData()
    val userType: LiveData<Event<UserTypes>> = _userType

    init {
        getUserType()
    }
    
    fun fillUpUser(usersType: List<UsersType>) {
        _usersType.value=usersType
    }

    private fun getUserType() {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.getUserType()
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _userType.value = Event(result.value)
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

    fun updateUserItem(userType: UsersType) {
        _type.value = userType
    }

    fun updateInUserTypeList(userType: UsersType) {

        val data = _usersType.value ?: return
        val updateData = updateToDefault(data)
        updateData.map {
            if (userType.id == it.id
            ) return@map userType
            else {
                return@map it
            }
        }.let {

            _usersType.value = it
        }

    }

    private fun updateToDefault(data: List<UsersType>): List<UsersType> {
        return data.map {
            it.copy(checked = false)
        }
    }

    fun validate(): Boolean {

        val data = _usersType.value ?: return false
        for (cat in data) {
            if (cat.checked) {
                return true
            }
        }
        return false
    }


}