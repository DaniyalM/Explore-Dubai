package com.app.dubaiculture.ui.preLogin.registeration.viewmodel

import android.app.Application
import android.widget.CompoundButton
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.registeration.RegistrationRepository
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.ui.preLogin.registeration.RegisterFragmentDirections
import com.app.dubaiculture.utils.AuthUtils
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.Error.INTERNET_CONNECTION_ERROR
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequest
import kotlinx.coroutines.launch
import timber.log.Timber

class RegistrationViewModel @ViewModelInject constructor(
    application: Application,
    private val registrationRepository: RegistrationRepository,
) : BaseViewModel(application) {


    // Validation
    var btnEnabled: ObservableBoolean = ObservableBoolean(false)

    // editext get() and set()
    var fullName: ObservableField<String> = ObservableField("")
    var email: ObservableField<String> = ObservableField("")
    var phone: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")
    var passwordConifrm: ObservableField<String> = ObservableField("")
    var termsAccepted: ObservableBoolean = ObservableBoolean(false)


    // booleans for checking regex validation
    val isPhone = MutableLiveData<Boolean?>(true)
    val isEmail = MutableLiveData<Boolean?>(true)
    val isfullName = MutableLiveData<Boolean?>(true)
    val isPassword = MutableLiveData<Boolean?>(true)
    val isPasswordConfirm = MutableLiveData<Boolean?>(true)
    val isTermAccepted = MutableLiveData<Boolean?>(true)

        // errors

        private var _fullnameError = MutableLiveData<Int>()
        var fullnameError: LiveData<Int> = _fullnameError

        private var _emailError = MutableLiveData<Int>()
        var emailError: LiveData<Int> = _emailError

        private var _phoneError = MutableLiveData<Int>()
        var phoneError: LiveData<Int> = _phoneError

    private var passwordError_ = MutableLiveData<Int>()
    var passwordError: LiveData<Int> = passwordError_

    private var passwordConfirmError_ = MutableLiveData<Int>()
    var passwordConfirmError: LiveData<Int> = passwordConfirmError_

    private val errs_ = MutableLiveData<Int>()
    val errs: LiveData<Int> = errs_


    fun register() {
        if (!isCheckValid())
            return
        viewModelScope.launch {
            showLoader(true)
            RegistrationRequest(
                email = email.get().toString().trim(),
                password = password.get().toString().trim(),
                confirmPassword = passwordConifrm.get().toString().trim(),
                fullName = fullName.get().toString().trim(),
                phoneNumber = phone.get().toString().trim()
            ).let {
                when (val result = registrationRepository.register(it)) {
                    is Result.Error -> {
                        showErrorDialog(message = result.exception.toString())
                    }
                    is Result.Success -> {
                        if (result.value.succeeded) {
                            navigateByDirections(
                                RegisterFragmentDirections.actionRegisterFragment2ToBottomSheet(
                                    result.value.registrationResponseDTO.verificationCode,
                                    "registerFragment"
                                )
                            )
                        } else {
                            showErrorDialog(message = result.value.errorMessage, colorBg = R.color.red_600)
                        }
                    }
                    is Result.Failure -> {
                        showErrorDialog(message = INTERNET_CONNECTION_ERROR, colorBg = R.color.red_600)
                        Timber.e(result.errorCode?.toString())
                    }
                }
            }
            showLoader(false)
        }
    }



    fun onFullNameChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        fullName.set(s.toString())
        if (fullName.get().toString().trim().isNullOrEmpty()) {
            isfullName.value = false
            _fullnameError.value = R.string.required
        }

        if (!fullName.get().toString().trim().isNullOrEmpty()) {
            isfullName.value = true
            _fullnameError.value = R.string.no_error
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

    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        password.set(s.toString())
        isPassword.value = AuthUtils.isValidPasswordFormat(s.toString().trim())
        passwordError_.value = AuthUtils.passwordErrors(s.toString().trim())
    }

    fun onConfirmPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        passwordConifrm.set(s.toString())
        isPasswordConfirm.value = AuthUtils.isMatchPasswordBool(password.get().toString(),
            passwordConifrm.get().toString().trim())
        passwordConfirmError_.value =
            AuthUtils.isMatchPasswordError(password.get().toString().trim(),
                passwordConifrm.get().toString().trim())

    }

    fun onTermsChecked(buttonView: CompoundButton, isChecked: Boolean) {
        termsAccepted.set(isChecked)
        isTermAccepted.value = isChecked
    }

   private fun isCheckValid(): Boolean {
        var isValid = true
        _emailError.value = AuthUtils.isEmailErrors(s = email.get().toString().trim())
        isEmail.value = AuthUtils.isEmailErrorsbool(email.get().toString().trim())


        val number = AuthUtils.isPhoneNumberValidate(phone.get().toString().trim())
        isPhone.value = number!!.isValid

        errs_.value = AuthUtils.errorsPhone(phone.get().toString().trim())
        isPassword.value = AuthUtils.isValidPasswordFormat(password.get().toString().trim())
        passwordError_.value = AuthUtils.passwordErrors(password.get().toString().trim())
        passwordConfirmError_.value =
            AuthUtils.isMatchPasswordError(password.get().toString().trim(),
                passwordConifrm.get().toString().trim())
        isPasswordConfirm.value = AuthUtils.isMatchPasswordBool(password.get().toString(),
            passwordConifrm.get().toString().trim())
        isTermAccepted.value = termsAccepted.get() != false




        if (fullName.get().toString().trim().isNullOrEmpty()) {
            isfullName.value = false
            _fullnameError.value = R.string.required
            isValid = false
        }

        if (!fullName.get().toString().trim().isNullOrEmpty()) {
            isfullName.value = true
            _fullnameError.value = R.string.no_error

        }

        if (!AuthUtils.isEmailErrorsbool(email.get().toString().trim())) {
            isValid = false
        }
        val phoneNum = AuthUtils.isPhoneNumberValidate(mobNumber = phone.get().toString().trim())
        if (!phoneNum!!.isValid) {
            isValid = false
        }

        if (!AuthUtils.isValidPasswordFormat(password.get().toString().trim())) {
            isValid = false
        }
        if (!AuthUtils.isMatchPasswordBool(password.get().toString(),
                passwordConifrm.get().toString().trim())
        ) {
            isValid = false
        }
        if (!termsAccepted.get()) {
            isValid = false
        }

        return isValid
    }
}
