package com.app.dubaiculture.ui.preLogin.registeration.viewmodel

import android.app.Application
import android.text.Editable
import android.util.Log
import android.util.Log.e
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
import com.app.dubaiculture.utils.getNonNull
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequest
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class RegistrationViewModel @ViewModelInject constructor(
    application: Application,
    private val registrationRepository: RegistrationRepository,
) : BaseViewModel(application) {


    // Validation
    var btnEnabled: ObservableBoolean = ObservableBoolean(false)

    // editext get() and set()
    var fullName: ObservableField<String> = ObservableField("")
    var email: ObservableField<String> = ObservableField("")
    var phone : ObservableField<String> = ObservableField("")
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
        if(!isCheckValid())
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
                        Timber.e(result.exception.message)
                        showToast(result.exception.message.toString())
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
                            showToast(result.value.errorMessage)
//                            showAlert(message = result.value.errorMessage)
//                            Timber.e(result.value.statusCode.toString())
                        }
                    }
                    is Result.Failure -> {
                        showToast("Internet Connection Error")
                        Timber.e(result.errorCode?.toString())
                    }
                }
            }
            showLoader(false)
        }
    }

    private fun isValidasd() {
        btnEnabled.set(
            fullName.get().toString().trim().isNotEmpty() ||
                    AuthUtils.isValidMobileNumber(phone.get().toString().trim()) ||
                    AuthUtils.isEmailValid(email.get().toString().trim()) ||
                    AuthUtils.isMatchPassword(password.get().toString().trim(),
                        passwordConifrm.get().toString().trim()) ||
                    AuthUtils.isValidPasswordFormat(password.get().toString().trim()) ||
                    termsAccepted.get())

    }


    fun onFullNameChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        fullName.set(s.toString())
        if(fullName.get().toString().trim().isNullOrEmpty()){
            isfullName.value = false
            _fullnameError.value = R.string.required
        }

        if(!fullName.get().toString().trim().isNullOrEmpty()){
            isfullName.value = true
            _fullnameError.value = R.string.no_error
        }
    }

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        email.set(s.toString())
//        isEmail.value = AuthUtils.isEmailValid(s.toString().trim())
        isEmail.value = AuthUtils.isEmailErrorsbool(s.toString().trim())
        _emailError.value = AuthUtils.isEmailErrors( s.toString().trim())
    }

    fun onPhoneChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        phone.set(s.toString())
//        isPhone.value = AuthUtils.isValidMobileNumber(s.toString().trim())
           val check            =  AuthUtils.isPhoneNumberValidate(s.toString().trim())
        if(check!!.isValid){
            Timber.e("Valid=${phone.get().toString().trim()}")
        }else{
            Timber.e("InValid=${phone.get().toString().trim()}")
        }
        errs_.value = AuthUtils.errorsPhone(s.toString().trim())
        Timber.e(phone.get().toString().trim())
    }

    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        password.set(s.toString())
        isPassword.value = AuthUtils.isValidPasswordFormat(s.toString().trim())
        passwordError_.value = AuthUtils.passwordErrors(s.toString().trim())
    }

    fun onConfirmPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        passwordConifrm.set(s.toString())
        isPasswordConfirm.value =AuthUtils.isMatchPasswordBool(password.get().toString(), passwordConifrm.get().toString().trim())
        passwordConfirmError_.value             = AuthUtils.isMatchPasswordError(password.get().toString().trim(),passwordConifrm.get().toString().trim())

//            AuthUtils.isMatchPassword(password.get().toString(), s.toString().trim())
    }

    fun onTermsChecked(buttonView: CompoundButton, isChecked: Boolean) {
        termsAccepted.set(isChecked)
        isTermAccepted.value = isChecked
    }

    fun isCheckValid():Boolean {
        var isValid = true
        _emailError.value = AuthUtils.isEmailErrors(s = email.get().toString().trim())
        isEmail.value =    AuthUtils.isEmailErrorsbool(email.get().toString().trim())




        isPhone.value = AuthUtils.isValidMobileNumber(phone = phone.get().toString().trim())

        errs_.value = AuthUtils.errorsPhone(phone.get().toString().trim())
        isPassword.value = AuthUtils.isValidPasswordFormat(password.get().toString().trim())
        passwordError_.value = AuthUtils.passwordErrors(password.get().toString().trim())
        passwordConfirmError_.value             = AuthUtils.isMatchPasswordError(password.get().toString().trim(),passwordConifrm.get().toString().trim())
        isPasswordConfirm.value = AuthUtils.isMatchPasswordBool(password.get().toString(), passwordConifrm.get().toString().trim())
        isTermAccepted.value = termsAccepted.get() != false




        if(fullName.get().toString().trim().isNullOrEmpty()){
            isfullName.value = false
            _fullnameError.value = R.string.required
            isValid = false
        }

        if(!fullName.get().toString().trim().isNullOrEmpty()){
            isfullName.value = true
            _fullnameError.value = R.string.no_error

        }

        if(!AuthUtils.isEmailErrorsbool(email.get().toString().trim())){
            isValid = false
        }
        if(!AuthUtils.isValidMobileNumber(phone = phone.get().toString().trim())){
            isValid = false
        }
        if(!AuthUtils.isValidPasswordFormat(password.get().toString().trim())){
            isValid = false
        }
        if(!AuthUtils.isMatchPasswordBool(password.get().toString(), passwordConifrm.get().toString().trim())){
            isValid = false
        }
        if( !termsAccepted.get()){
            isValid = false
        }

        return isValid
    }
}
