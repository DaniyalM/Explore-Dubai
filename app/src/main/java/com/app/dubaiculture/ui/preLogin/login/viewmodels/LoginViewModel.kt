package com.app.dubaiculture.ui.preLogin.login.viewmodels

import android.app.Application
import android.text.Editable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.login.LoginRepository
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest
import com.app.dubaiculture.data.repository.user.UserRepository
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.AuthUtils
import com.app.dubaiculture.utils.event.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    application: Application,
) : BaseViewModel(application) {
    var phone: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")
    var btnSelected: ObservableBoolean = ObservableBoolean(false)


    val isPhone = MutableLiveData<Boolean?>(true)
    val isEmail = MutableLiveData<Boolean?>(true)

    val isPhoneEdit = MutableLiveData<Boolean?>(true)
    val isEmailEdit = MutableLiveData<Boolean?>(true)

    val isPassword = MutableLiveData<Boolean?>(true)

    //boolean for checking typing email or number
    val isLoginWithPhone = MutableLiveData<Boolean?>(false)

    val mobileNumberError = MutableLiveData<String?>()

    val phoneError = MutableLiveData<Boolean?>(false)

    val emailError = MutableLiveData<Boolean?>(false)


    private var _loginStatus: MutableLiveData<Event<Boolean>> = MutableLiveData()
    var loginStatus: MutableLiveData<Event<Boolean>> = _loginStatus

    fun loginWithPhone() {
        viewModelScope.launch {
            showLoader(true)
            LoginRequest(
                phoneNumber = phone.get().toString().trim(),
                password = password.get().toString().trim()
            ).let {
                when (val result = loginRepository.login(it)) {
                    is Result.Success -> {
                        if (result.value.succeeded) {
                            Timber.e(result.value.loginResponseDTO.userDTO.Email)
                            userRepository.saveUser(
                                userDTO = result.value.loginResponseDTO.userDTO,
                                loginResponseDTO = result.value.loginResponseDTO
                            )
                            _loginStatus.value = Event(true)
                        } else {
                            showToast(result.value.errorMessage)
                        }
                    }
                    is Result.Error -> {
                        showToast(result.exception.toString())
                    }
                    is Result.Failure -> {
                        showToast(result.errorCode.toString())
                    }
                }
            }
            showLoader(false)
        }
    }


    fun loginWithEmail() {
        viewModelScope.launch {
            showLoader(true)
            LoginRequest(
                email = phone.get().toString().trim(),
                password = password.get().toString().trim()
            ).let {
                when (val result = loginRepository.loginWithEmail(it)) {
                    is Result.Success -> {
                        if (result.value.succeeded) {
                            Timber.e(result.value.loginResponseDTO.userDTO.Email)
                            userRepository.saveUser(
                                userDTO = result.value.loginResponseDTO.userDTO,
                                loginResponseDTO = result.value.loginResponseDTO
                            )
                            _loginStatus.value = Event(true)
                        } else {
                            showToast(result.value.errorMessage)
                        }
                    }
                    is Result.Error -> {
                        showToast(result.exception.toString())
                    }
                    is Result.Failure -> {
                        showToast(result.errorCode.toString())
                    }
                }
            }
            showLoader(false)
        }
    }


    fun onPhoneChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        phone.set(s.toString())
        isLoginWithPhone.value = loginTypeChecker(s)
        enableButton()
        isPhoneEdit.value = AuthUtils.isValidMobileNumber(s.toString().trim())
        isEmailEdit.value = AuthUtils.isEmailValid(s.toString().trim())

        phoneError.value = !s.contains("[a-zA-Z]".toRegex())
        emailError.value = s.contains("[a-zA-Z]".toRegex())
    }

    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        password.set(s.toString())
        isPassword.value = AuthUtils.isValidPasswordFormat(s.toString().trim())
    }

    private fun loginTypeChecker(s: CharSequence): Boolean {
        return if (!s.contains("[a-zA-Z]".toRegex())) {
            isPhone.value = AuthUtils.isValidMobileNumber(s.toString().trim())
            Timber.e("Phone")
            true
        } else {
            isEmail.value = AuthUtils.isEmailValid(s.toString().trim())
            Timber.e("Email")
            false
        }
    }


    private fun enableButton() {
        if ((AuthUtils.isValidMobileNumber(phone.get().toString().trim()) &&
                    AuthUtils.isValidPasswordFormat(password.get().toString()
                        .trim())) || (AuthUtils.isEmailValid(phone.get().toString().trim()) &&
                    AuthUtils.isValidPasswordFormat(password.get().toString().trim()))
        ) {
            btnSelected.set(true)
        } else {
            btnSelected.set(false)
        }
    }

}