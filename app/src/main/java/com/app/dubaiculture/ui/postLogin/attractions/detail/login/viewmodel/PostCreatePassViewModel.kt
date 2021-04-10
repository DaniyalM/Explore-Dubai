package com.app.dubaiculture.ui.postLogin.attractions.detail.login.viewmodel

import android.app.Application
import androidx.core.os.bundleOf
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.setpassword.SetPasswordRepository
import com.app.dubaiculture.data.repository.setpassword.remote.request.SetPasswordRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.AuthUtils
import com.app.dubaiculture.utils.Constants
import kotlinx.coroutines.launch
import timber.log.Timber


class PostCreatePassViewModel @ViewModelInject constructor(
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

    fun setPassword(
        verificationCode: String? = null,
    ) {
        if(!isCheckValid())
            return
        viewModelScope.launch {
            showLoader(true)
            SetPasswordRequest(
                verificationToken = verificationCode ?: "",
                newPassword = password.get().toString().trim(),
                confirmPassword =passwordConifrm.get().toString().trim()
            ).let {
                when (val result = setPasswordRepository.setPassword(it)) {
                    is com.app.dubaiculture.data.Result.Success -> {
                        showLoader(false)
                        if (result.value.succeeded) {
                            showToast(result.value.setPasswordResponseDTO.message)
                            val bundle = bundleOf("post" to "postFragment")
                            navigateByAction(R.id.action_postCreatePassFragment_to_passwordUpdatedFragment2,bundle)
                        } else {
                            showErrorDialog(message = result.value.errorMessage)
                        }
                    }
                    is com.app.dubaiculture.data.Result.Failure -> {
                        showLoader(false)
                        showErrorDialog(message = Constants.Error.INTERNET_CONNECTION_ERROR)
                        Timber.e(result.errorCode.toString())
                    }
                    is com.app.dubaiculture.data.Result.Error -> {
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

        eightCharacter.value = s.toString().length>=8
        upperCaseLetter.value = s.contains("(?=.*[A-Z])".toRegex())
        lowerCaseLetter.value = s.contains("(?=.*[a-z])".toRegex())
        oneDigit.value = s.contains("(.*\\d.*)".toRegex())
        specialCharacter.value = s.contains("(?=.*[<>/?!@#\$%^,*()&+=~.])".toRegex())

    }
    fun onConfirmPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        passwordConifrm.set(s.toString())
        isPasswordConfirm.value =AuthUtils.isMatchPasswordBool(password.get().toString(), passwordConifrm.get().toString().trim())
        passwordConfirmError_.value             = AuthUtils.isMatchPasswordError(password.get().toString().trim(),passwordConifrm.get().toString().trim())
    }

    private  fun isCheckValid():Boolean{
        var isValid = true
        isPassword.value = AuthUtils.isValidPasswordFormat(password.get().toString().trim())
        isPasswordConfirm.value = AuthUtils.isMatchPasswordBool(password.get().toString(), passwordConifrm.get().toString().trim())

        passwordError_.value = AuthUtils.passwordErrors(password.get().toString().trim())
        passwordConfirmError_.value   = AuthUtils.isMatchPasswordError(password.get().toString().trim(),passwordConifrm.get().toString().trim())

        if(!AuthUtils.isValidPasswordFormat(password.get().toString().trim())){
            isValid = false
        }
        if(!AuthUtils.isMatchPasswordBool(password.get().toString(), passwordConifrm.get().toString().trim())){
            isValid = false
        }
        return isValid
    }
}