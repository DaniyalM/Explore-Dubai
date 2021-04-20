package com.app.dubaiculture.ui.preLogin.forgot.viewModel

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.forgot.ForgotRepository
import com.app.dubaiculture.data.repository.forgot.remote.request.ForgotRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.ui.preLogin.forgot.ForgotFragmentDirections
import com.app.dubaiculture.utils.AuthUtils
import com.app.dubaiculture.utils.Constants
import kotlinx.coroutines.launch
import timber.log.Timber

class ForgotViewModel @ViewModelInject constructor(private val forgotRepository: ForgotRepository, application: Application): BaseViewModel(application) {

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
        _emailError.value = AuthUtils.isEmailErrors( s.toString().trim())
    }

    fun forgotEmail(){
        if(!isCheckValid())
            return
        viewModelScope.launch {
            showLoader(true)
            ForgotRequest(
                email = email.get().toString().trim()
            ).let {
                when(val result =forgotRepository.forgot(it)){
                    is Result.Success->{
                        if(result.value.succeeded){
                            showLoader(false)
                            Timber.e(result.value.forgotResponseDTO.verificationCode)
                            showErrorDialog(message = result.value.forgotResponseDTO.message.toString())
                            navigateByDirections(ForgotFragmentDirections.actionForgotFragmentToBottomSheet(
                                result.value.forgotResponseDTO.verificationCode,"forgotfragment"
                            ))
                        }else{
                            showErrorDialog(message = result.value.errorMessage,colorBg =  R.color.red_600)
                        }
                    }
                    is Result.Error->{
                        showLoader(false)
                        Timber.e(result.exception)
                    }
                    is Result.Failure->{
                        showLoader(false)
                        showErrorDialog(message = Constants.Error.INTERNET_CONNECTION_ERROR)
                        Timber.e(result.errorCode.toString())
                    }
                }
            }
            showLoader(false)
        }
    }

  private  fun isCheckValid():Boolean{
        var isValid = true
        _emailError.value = AuthUtils.isEmailErrors(s = email.get().toString().trim())
        isEmail.value =    AuthUtils.isEmailErrorsbool(email.get().toString().trim())
        if(!AuthUtils.isEmailErrorsbool(email.get().toString().trim())){
            isValid = false
        }
        return isValid
    }
}