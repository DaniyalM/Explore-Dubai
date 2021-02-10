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
import com.app.dubaiculture.utils.AuthUtils
import kotlinx.coroutines.launch
import timber.log.Timber

class ForgotViewModel @ViewModelInject constructor(private val forgotRepository: ForgotRepository, application: Application): BaseViewModel(application) {

    var email: ObservableField<String> = ObservableField("")
    //boolean for visibility of hide and gone of img error
    val isEmail = MutableLiveData<Boolean?>(true)
     // Field Error Messages
     val emailError = MutableLiveData<String?>()
    // for button enable and disable
    var btnEnabled: ObservableBoolean = ObservableBoolean(false)


    // for button enable disable
    fun enableButton() {
        btnEnabled.set(
            AuthUtils.isEmailValid(email.get().toString().trim())
        )
    }

    fun forgotEmail(){
        viewModelScope.launch {
            showLoader(true)
            ForgotRequest(
                email = "ammar@gmail.com"
            ).let {
                when(val result =forgotRepository.forgot(it)){
                    is Result.Success->{
                        if(result.value.succeeded){
                            showLoader(false)
                            showToast(result.value.forgotResponseDTO.message.toString())
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