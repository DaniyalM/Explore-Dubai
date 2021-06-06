package com.app.dubaiculture.ui.postLogin.more.profile.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
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
import com.app.dubaiculture.utils.AuthUtils
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



    // booleans for checking regex validation
    val isPhone = MutableLiveData<Boolean?>(true)
    val isEmail = MutableLiveData<Boolean?>(true)

    // editext get() and set()
    var email: ObservableField<String> = ObservableField("")
    var phone: ObservableField<String> = ObservableField("")

    // errors
    private var _emailError = MutableLiveData<Int>()
    var emailError: LiveData<Int> = _emailError

    private var _phoneError = MutableLiveData<Int>()
    var phoneError: LiveData<Int> = _phoneError
    private val errs_ = MutableLiveData<Int>()
    val errs: LiveData<Int> = errs_

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
            showLoader(true)
            val result = profileRepository.getFavourites()
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _favourite.value=result.value
                }
                is Result.Error -> result
                is Result.Failure -> result
            }
        }
    }
    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        email.set(s.toString())
        isEmail.value = AuthUtils.isEmailErrorsbool(s.toString().trim())
        _emailError.value = AuthUtils.isEmailErrors(s.toString().trim())
    }

    fun onPhoneChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        phone.set(s.toString())
        val number   = AuthUtils.isPhoneNumberValidate(s.toString().trim())
        isPhone.value = number!!.isValid
        errs_.value = AuthUtils.errorsPhone(s.toString().trim())
    }


    fun isCheckValidation():Boolean{
        var isValid = true
        _emailError.value = AuthUtils.isEmailErrors(s = email.get().toString().trim())
        isEmail.value = AuthUtils.isEmailErrorsbool(email.get().toString().trim())
        val number = AuthUtils.isPhoneNumberValidate(phone.get().toString().trim())
        isPhone.value = number!!.isValid
        errs_.value = AuthUtils.errorsPhone(phone.get().toString().trim())
        if (!AuthUtils.isEmailErrorsbool(email.get().toString().trim())) {
            isValid = false
        }
        val phoneNum = AuthUtils.isPhoneNumberValidate(mobNumber = phone.get().toString().trim())
        if (!phoneNum!!.isValid) {
            isValid = false
        }
        return isValid
    }
}