package com.app.dubaiculture.ui.preLogin.forgot.viewModel

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.forgot.ForgotRepository
import com.app.dubaiculture.data.repository.forgot.remote.request.ForgotRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.ui.preLogin.forgot.ForgotFragmentDirections
import com.app.dubaiculture.ui.preLogin.registeration.RegisterFragmentDirections
import com.app.dubaiculture.utils.AuthUtils
import kotlinx.coroutines.launch
import timber.log.Timber

class ForgotViewModel @ViewModelInject constructor(private val forgotRepository: ForgotRepository, application: Application): BaseViewModel(application) {

    var email: ObservableField<String> = ObservableField("")
    //boolean for visibility of hide and gone of img error
    val isEmail = MutableLiveData<Boolean?>(true)
    // for button enable and disable
    var btnEnabled: ObservableBoolean = ObservableBoolean(false)

    // for button enable disable
   private fun enableButton() {
        btnEnabled.set(
            AuthUtils.isEmailValid(email.get().toString().trim())
        )
    }
    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        email.set(s.toString())
        isEmail.value = AuthUtils.isEmailValid(s.toString().trim())
        enableButton()
    }

    fun forgotEmail(){
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

                            showToast(result.value.forgotResponseDTO.message.toString())

                            navigateByDirections(ForgotFragmentDirections.actionForgotFragmentToBottomSheet(
                                result.value.forgotResponseDTO.verificationCode,"forgotfragment"
                            ))
                        }else{
                            showToast(result.value.errorMessage)
                        }
                    }
                    is Result.Error->{
                        Timber.e(result.exception)

                    }
                    is Result.Failure->{
                        Timber.e(result.errorCode.toString())
                    }
                }
            }
            showLoader(false)
        }
    }
}