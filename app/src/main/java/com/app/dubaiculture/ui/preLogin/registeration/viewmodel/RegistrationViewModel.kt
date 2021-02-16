package com.app.dubaiculture.ui.preLogin.registeration.viewmodel

import android.app.Application
import android.text.Editable
import android.util.Log
import android.widget.CompoundButton
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.registeration.RegistrationRepository
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.ui.preLogin.registeration.RegisterFragmentDirections
import com.app.dubaiculture.utils.AuthUtils
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequest
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.launch
import timber.log.Timber

class RegistrationViewModel @ViewModelInject constructor(
    application: Application,
    private val registrationRepository: RegistrationRepository
) : BaseViewModel(application) {


    // Validation
    var btnEnabled: ObservableBoolean = ObservableBoolean(false)

    var fullName: ObservableField<String> = ObservableField("")
    var email: ObservableField<String> = ObservableField("")
    var phone: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")
    var passwordConifrm: ObservableField<String> = ObservableField("")
    var termsAccepted: ObservableBoolean = ObservableBoolean(false)


    // booleans
    val isPhone = MutableLiveData<Boolean?>(true)
    val isEmail = MutableLiveData<Boolean?>(true)
    val isfullName = MutableLiveData<Boolean?>(true)
    val isPassword = MutableLiveData<Boolean?>(true)
    val isPasswordConfirm = MutableLiveData<Boolean?>(true)
    val isTermAccepted = MutableLiveData<Boolean?>(true)



 private   var passwordError_= MutableLiveData<Int>()
    var passwordError: LiveData<Int> =passwordError_



    fun register() {
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
                                    result.value.registrationResponseDTO.verificationCode,"registerFragment"
                                )
                            )
                        } else {
                            showToast(result.value.errorMessage)
//                            showAlert(message = result.value.errorMessage)
//                            Timber.e(result.value.statusCode.toString())
                        }
                    }
                    is Result.Failure -> {
                        showToast(result.errorCode.toString())
                        Timber.e(result.errorCode?.toString())
                    }
                }
            }
            showLoader(false)
        }
    }

   private fun enableButton() {
        btnEnabled.set(
            fullName.get().toString().trim().isNotEmpty() &&
                    AuthUtils.isValidMobileNumber(phone.get().toString().trim()) &&
                    AuthUtils.isEmailValid(email.get().toString().trim()) &&
                    AuthUtils.isMatchPassword(password.get().toString().trim(),passwordConifrm.get().toString().trim()) &&
                    AuthUtils.isValidPasswordFormat(password.get().toString().trim())&&
                    termsAccepted.get()
        )
    }



    fun onFullNameChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        fullName.set(s.toString())
        isfullName.value = s.toString().trim().isNotEmpty()
        enableButton()
    }
    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        email.set(s.toString())
        isEmail.value = AuthUtils.isEmailValid(s.toString().trim())
        enableButton()
    }
    fun onPhoneChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        phone.set(s.toString())
        isPhone.value = AuthUtils.isValidMobileNumber(s.toString().trim())
        Timber.e(phone.get().toString().trim())
        enableButton()
    }
    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        password.set(s.toString())
         isPassword.value =   AuthUtils.isValidPasswordFormat(s.toString().trim())
        passwordError_.value      = AuthUtils.passwordErrors(s.toString().trim())

        enableButton()
    }
    fun onConfirmPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        passwordConifrm.set(s.toString())
        isPasswordConfirm.value = AuthUtils.isMatchPassword(password.get().toString(),s.toString().trim())
        enableButton()
    }

    fun onTermsChecked(buttonView: CompoundButton, isChecked: Boolean) {
        termsAccepted.set(isChecked)
        isTermAccepted.value = isChecked
        enableButton()
    }


}