package com.dubaiculture.ui.postLogin.profile.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.profile.ProfileRepository
import com.dubaiculture.data.repository.settings.local.UserSettings
import com.dubaiculture.data.repository.user.UserRepository
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.AuthUtils
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    application: Application,
    private val profileRepository: ProfileRepository,
    private val userRepository: UserRepository,
) :
    BaseViewModel(application, profileRepository) {
    private val _userSetting: MutableLiveData<Result<Event<UserSettings>>> = MutableLiveData()
    val userSettings: LiveData<Result<Event<UserSettings>>> = _userSetting
//
//    private val _favourite: MutableLiveData<Result<Event<Favourite>>> = MutableLiveData()
//    val favourite: LiveData<Result<Event<Favourite>>> = _favourite


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

//    init {
//
//    }

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
                    showLoader(false)
                    _userSetting.value = result
                }
                is Result.Error -> {
                    showLoader(false)
                    _userSetting.value = result
                }
                is Result.Failure -> {
                    showLoader(false)
                    _userSetting.value = result
                }
            }
        }

    }

    fun updateSettings(settings: UserSettings, isRefresh: Boolean = false) {
        viewModelScope.launch {
            showLoader(true)
            val result = profileRepository.updateSettings(userSettings = settings)
            when (result) {
                is Result.Success -> {
                    showToast("Permissions Updated !")
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

//
//    fun getFavourites() {
//        viewModelScope.launch {
//            showLoader(true)
//            val result = profileRepository.getFavourites()
//            when (result) {
//                is Result.Success -> {
//                    showLoader(false)
//                    _favourite.value = result
//                }
//                is Result.Error -> {
//                    _favourite.value = result
//                }
//                is Result.Failure -> {
//                    _favourite.value = result
//                }
//            }
//        }
//    }

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        email.set(s.toString())
        isEmail.value = AuthUtils.isEmailErrorsbool(s.toString().trim())
        _emailError.value = AuthUtils.isEmailErrors(s.toString().trim())
    }

    fun onPhoneChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        phone.set(s.toString())
        val number = AuthUtils.isPhoneNumberValidate(s.toString().trim())
        isPhone.value = number!!.isValid
        errs_.value = AuthUtils.errorsPhone(s.toString().trim())
    }


    fun isCheckValidation(): Boolean {
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