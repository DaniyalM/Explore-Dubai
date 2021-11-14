package com.dubaiculture.ui.postLogin.login.viewmodel

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.forgot.ForgotRepository
import com.dubaiculture.data.repository.forgot.remote.request.ForgotRequest
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.ui.postLogin.login.PostForgotFragmentDirections
import com.dubaiculture.utils.AuthUtils
import com.dubaiculture.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostForgotViewModel @Inject constructor(
    private val forgotRepository: ForgotRepository,
    application: Application
) : BaseViewModel(application) {

    var email: ObservableField<String> = ObservableField("")

    //boolean for visibility of hide and gone of img error
    val isEmail = MutableLiveData<Boolean?>(true)

    // for button enable and disable
    var btnEnabled: ObservableBoolean = ObservableBoolean(false)

    private var _emailError = MutableLiveData<Int>()
    var emailError: LiveData<Int> = _emailError

    // for button enable disable
    private fun enableButton() {
        btnEnabled.set(
            AuthUtils.isEmailValid(email.get().toString().trim())
        )
    }

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        email.set(s.toString())
        isEmail.value = AuthUtils.isEmailErrorsbool(s.toString().trim())
        _emailError.value = AuthUtils.isEmailErrors(s.toString().trim())
    }

    fun forgotEmail() {
        if (!isCheckValid())
            return
        viewModelScope.launch {
            showLoader(true)
            ForgotRequest(
                email = email.get().toString().trim()
            ).let {
                when (val result = forgotRepository.forgot(it)) {
                    is Result.Success -> {
                        if (result.value.succeeded) {
                            showLoader(false)
                            Timber.e(result.value.forgotResponseDTO.verificationCode)
//                            showToast(result.value.forgotResponseDTO.message.toString())
                            showErrorDialog(message = result.value.forgotResponseDTO.message.toString())

                            navigateByDirections(
                                PostForgotFragmentDirections.actionPostForgotFragmentToPostOTPDialogFragment(
                                    result.value.forgotResponseDTO.verificationCode,
                                    "forgotfragment"
                                )
                            )
                        } else {
                            showErrorDialog(
                                message = result.value.errorMessage,
                                colorBg = R.color.red_600
                            )
                        }
                    }
                    is Result.Error -> {
                        showLoader(false)
                        Timber.e(result.exception)
                    }
                    is Result.Failure -> {
                        showLoader(false)
                        showErrorDialog(
                            message = Constants.Error.INTERNET_CONNECTION_ERROR,
                            colorBg = R.color.red_600
                        )
                        Timber.e(result.errorCode.toString())
                    }
                }
            }
            showLoader(false)
        }
    }

    private fun isCheckValid(): Boolean {
        var isValid = true
        _emailError.value = AuthUtils.isEmailErrors(s = email.get().toString().trim())
        isEmail.value = AuthUtils.isEmailErrorsbool(email.get().toString().trim())
        if (!AuthUtils.isEmailErrorsbool(email.get().toString().trim())) {
            isValid = false
        }
        return isValid
    }
}