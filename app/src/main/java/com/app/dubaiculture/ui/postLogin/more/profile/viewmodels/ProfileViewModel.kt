package com.app.dubaiculture.ui.postLogin.more.profile.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.profile.ProfileRepository
import com.app.dubaiculture.data.repository.profile.local.Favourite
import com.app.dubaiculture.data.repository.settings.local.UserSettings
import com.app.dubaiculture.data.repository.user.UserRepository
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
        application: Application,
        private val profileRepository: ProfileRepository,
        private val userRepository: UserRepository,
) :
        BaseViewModel(application,profileRepository) {
    private val _userSetting: MutableLiveData<Event<UserSettings>> = MutableLiveData()
    val userSettings: LiveData<Event<UserSettings>> = _userSetting

    private val _favourite: MutableLiveData<Event<Favourite>> = MutableLiveData()
    val favourite: LiveData<Event<Favourite>> = _favourite


    fun uploadProfile(uri: String, application: ApplicationEntry) {
        viewModelScope.launch {
            showLoader(true)
            val result = profileRepository.uploadProfilePicture(uri)
            if (result is Result.Success) {
                var url = result.value.result.userImage
                val info = userRepository.getLastUser()
                info?.apply {

                    userImage = url
                    userImageUri = uri
                    userRepository.updateUser(this)
                    application.auth.user = this
                }
            } else if (result is Result.Failure) {
                showToast(result.errorMessage!!)
            }
            showLoader(false)
        }
    }


    fun getSettings() {
        viewModelScope.launch {
            showLoader(true)
            val result = profileRepository.getSettings()
            when (result) {
                is Result.Success -> {
                    _userSetting.value = result.value
                    showLoader(false)
                }
                is Result.Error -> result.exception
                is Result.Failure -> result.isNetWorkError
            }
        }

    }

    fun updateSettings(settings: UserSettings, isRefresh: Boolean = false) {
        viewModelScope.launch {
            showLoader(true)
            val result = profileRepository.updateSettings(userSettings = settings)
            when (result) {
                is Result.Success -> {
                    showToast(result.message)
                    showLoader(false)
                    if (isRefresh) {
                        getSettings()
                    }
                }
                is Result.Error -> result.exception
                is Result.Failure -> result.isNetWorkError
            }
        }

    }


    fun getFavourites() {
        viewModelScope.launch {
            val result = profileRepository.getFavourites()
            when (result) {
                is Result.Success -> {
                    _favourite.value=result.value
                }
                is Result.Error -> result
                is Result.Failure -> result
            }
        }
    }


}