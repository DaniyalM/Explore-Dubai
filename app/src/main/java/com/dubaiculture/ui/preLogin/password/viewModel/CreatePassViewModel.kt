package com.dubaiculture.ui.preLogin.password.viewModel

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.setpassword.SetPasswordRepository
import com.dubaiculture.data.repository.setpassword.remote.request.SetPasswordRequest
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.AuthUtils
import com.dubaiculture.utils.Constants.Error.INTERNET_CONNECTION_ERROR
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreatePassViewModel @Inject constructor(
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

    private var passwordError_ = MutableLiveData<Int>()
    var passwordError: LiveData<Int> = passwordError_

    private var passwordConfirmError_ = MutableLiveData<Int>()
    var passwordConfirmError: LiveData<Int> = passwordConfirmError_
    private var isPasswordSet_ = MutableLiveData<Event<Boolean>>()
    var isPasswordSet: LiveData<Event<Boolean>> = isPasswordSet_

    fun setPassword(
        verificationCode: String? = null,
    ) {
        if (!isCheckValid())
            return
        viewModelScope.launch {
            showLoader(true)
            SetPasswordRequest(
                verificationToken = verificationCode ?: "",
                newPassword = password.get().toString().trim(),
                confirmPassword = passwordConifrm.get().toString().trim()
            ).let {
                when (val result = setPasswordRepository.setPassword(it)) {
                    is Result.Success -> {
                        showLoader(false)
                        if (result.value.succeeded) {
                            isPasswordSet_.value=Event(true)
                            showToast(result.value.setPasswordResponseDTO.message)
                        } else {
                            showErrorDialog(message = result.value.errorMessage)
                        }
                    }
                    is Result.Failure -> {
                        showLoader(false)
                        showErrorDialog(message = INTERNET_CONNECTION_ERROR)
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
        isPassword.value = AuthUtils.isValidPasswordFormat(s.toString().trim())
        passwordError_.value = AuthUtils.passwordErrors(s.toString().trim())

        eightCharacter.value = s.toString().length >= 8
        upperCaseLetter.value = s.contains("(?=.*[A-Z])".toRegex())
        lowerCaseLetter.value = s.contains("(?=.*[a-z])".toRegex())
        oneDigit.value = s.contains("(.*\\d.*)".toRegex())
        specialCharacter.value = s.contains("(?=.*[<>/?!@#\$%^,*()&+=~.])".toRegex())

    }

    fun onConfirmPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        passwordConifrm.set(s.toString())
        isPasswordConfirm.value = AuthUtils.isMatchPasswordBool(
            password.get().toString(),
            passwordConifrm.get().toString().trim()
        )
        passwordConfirmError_.value = AuthUtils.isMatchPasswordError(
            password.get().toString().trim(),
            passwordConifrm.get().toString().trim()
        )
    }

    private fun isCheckValid(): Boolean {
        var isValid = true
        isPassword.value = AuthUtils.isValidPasswordFormat(password.get().toString().trim())
        isPasswordConfirm.value = AuthUtils.isMatchPasswordBool(
            password.get().toString(),
            passwordConifrm.get().toString().trim()
        )

        passwordError_.value = AuthUtils.passwordErrors(password.get().toString().trim())
        passwordConfirmError_.value = AuthUtils.isMatchPasswordError(
            password.get().toString().trim(),
            passwordConifrm.get().toString().trim()
        )

        if (!AuthUtils.isValidPasswordFormat(password.get().toString().trim())) {
            isValid = false
        }
        if (!AuthUtils.isMatchPasswordBool(
                password.get().toString(),
                passwordConifrm.get().toString().trim()
            )
        ) {
            isValid = false
        }
        return isValid
    }
}