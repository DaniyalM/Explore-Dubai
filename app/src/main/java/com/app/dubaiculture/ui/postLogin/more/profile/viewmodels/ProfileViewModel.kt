package com.app.dubaiculture.ui.postLogin.more.profile.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.profile.ProfileRepository
import com.app.dubaiculture.data.repository.user.UserRepository
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
        application: Application,
        private val profileRepository: ProfileRepository,
        private val userRepository: UserRepository,
        ) :
        BaseViewModel(application) {




    fun uploadProfile(uri: String) {
        viewModelScope.launch {
            showLoader(true)
            val result = profileRepository.uploadProfilePicture(uri)
            if (result is Result.Success) {
                val url = result.value.value
                val info = userRepository.getLastUser()
                info?.apply {
                    userImage = url
                    userRepository.updateUser(this)
                }
            } else if (result is Result.Failure) {
                showToast(result.errorMessage!!)
            }
            showLoader(false)
        }
    }





}