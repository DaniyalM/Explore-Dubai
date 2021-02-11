package com.app.dubaiculture.ui.preLogin.password.viewModel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.setpassword.SetPasswordRepository
import com.app.dubaiculture.data.repository.setpassword.remote.request.SetPasswordRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.AuthUtils
import kotlinx.coroutines.launch
import timber.log.Timber

class CreatePassViewModel @ViewModelInject constructor(
    private val setPasswordRepository: SetPasswordRepository,
    application: Application
) :
    BaseViewModel(application) {

    var btnEnabled: ObservableBoolean = ObservableBoolean(false)

    var password: ObservableField<String> = ObservableField("")
    var passwordConifrm: ObservableField<String> = ObservableField("")

    val isPassword = MutableLiveData<Boolean?>(true)
    val isPasswordConfirm = MutableLiveData<Boolean?>(true)

    val eightCharacter = MutableLiveData<Boolean?>(false)
    val upperCaseLetter = MutableLiveData<Boolean?>(false)
    val lowerCaseLetter = MutableLiveData<Boolean?>(false)
    val specialCharacter = MutableLiveData<Boolean?>(false)
    val oneDigit = MutableLiveData<Boolean?>(false)



    private fun enableButton() {
        btnEnabled.set(
            AuthUtils.isMatchPassword(
                password.get().toString().trim(),
                passwordConifrm.get().toString().trim()
            ) && AuthUtils.isValidPasswordFormat(password.get().toString().trim())
        )
    }

    fun setPassword(
        verificationCode: String? = null,
    ) {
        viewModelScope.launch {
            showLoader(true)
            SetPasswordRequest(
                verificationToken = verificationCode ?: "",
                newPassword = password.get().toString().trim(),
                confirmPassword =passwordConifrm.get().toString().trim()
            ).let {
                when (val result = setPasswordRepository.setPassword(it)) {
                    is Result.Success -> {
                        showLoader(false)
                        if (result.value.succeeded) {
                            showToast(result.value.setPasswordResponseDTO.message)
                            navigateByAction(R.id.action_createPassFragment_to_passwordUpdatedFragment)
                        } else {
                            showToast(result.value.errorMessage)
                        }
                    }
                    is Result.Failure -> {
                        Timber.e(result.errorCode.toString())
                    }
                    is Result.Error -> {
                        Timber.e(result.exception)
                    }
                }
            }
            showLoader(false)
        }
    }
    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        password.set(s.toString())
        isPassword.value =   AuthUtils.isValidPasswordFormat(s.toString().trim())
        enableButton()

        eightCharacter.value = s.toString().length>=8
        upperCaseLetter.value = s.contains("(?=.*[A-Z])".toRegex())
        lowerCaseLetter.value = s.contains("(?=.*[a-z])".toRegex())
        oneDigit.value = s.contains("(.*\\d.*)".toRegex())
        specialCharacter.value = s.contains("(?=.*[<>/?!@#\$%^,*()&+=~.])".toRegex())

    }
    fun onConfirmPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        passwordConifrm.set(s.toString())
        isPasswordConfirm.value = AuthUtils.isMatchPassword(password.get().toString(),s.toString().trim())
        enableButton()
    }
}