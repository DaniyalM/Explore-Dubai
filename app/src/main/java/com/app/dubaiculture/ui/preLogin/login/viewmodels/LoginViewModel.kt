package com.app.dubaiculture.ui.preLogin.login.viewmodels

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.login.LoginRepository
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.AuthUtils
import com.app.dubaiculture.utils.AuthUtils.isEmailValid
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel @ViewModelInject constructor( private val loginRepository: LoginRepository,
    application: Application
) : BaseViewModel(application) {
    var email: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")
    var btnSelected: ObservableBoolean = ObservableBoolean(false)


    val isEmail = MutableLiveData<Boolean?>(true)
    val isPassword = MutableLiveData<Boolean?>(true)

    //Below loginStatus Flag Will be removed as per requirement
    private var _emailStatus: MutableLiveData<Boolean> = MutableLiveData()
    var emailStatus: MutableLiveData<Boolean> = _emailStatus
    private  var _passwordStatus: MutableLiveData<Boolean> = MutableLiveData()
    var passwordStatus: MutableLiveData<Boolean> = _passwordStatus

    private var _loginStatus: MutableLiveData<Boolean> = MutableLiveData()
    var loginStatus: MutableLiveData<Boolean> = _loginStatus
    init {
        _loginStatus.value = false
        _emailStatus.value = false
        _passwordStatus.value = false
    }
//    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
//        btnSelected.set(
//            isEmailValid(s.toString().trim()) &&
//                    password.get().toString().trim().length >= 6
//        )
//        _emailStatus.value = !isEmailValid(s.toString().trim())
//    }
//
//    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
//        btnSelected.set(
//            isEmailValid(email.get().toString().trim()) &&
//                    s.toString().trim().length >= 6
//        )
//        _passwordStatus.value = s.toString().trim().length < 6
//    }


    fun login() {
//        loginStatus.value = true
        viewModelScope.launch {
            showLoader(true)
            LoginRequest(
                email = "ammar10@yopmail.com",
                password = "Pakistan@1"
            ).let {
               when(val result = loginRepository.login(it)){
                   is Result.Success ->{
                       if(result.value.succeeded){
                           Timber.e(result.value.loginResponseDTO.userDTO.Email)
                       } else {
                           showToast(result.value.errorMessage)
                       }
                   }
                   is Result.Error ->{
                       showToast(result.exception.toString())
                   }
                   is Result.Failure ->{
                       showToast(result.errorCode.toString())
                   }
               }
            }
            showLoader(false)

        }
    }

    fun enableButton() {
        btnSelected.set(
                    AuthUtils.isEmailValid(email.get().toString().trim()) &&
                    AuthUtils.isValidPasswordFormat(password.get().toString().trim())
        )
    }


    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        email.set(s.toString())
        isEmail.value = AuthUtils.isEmailValid(s.toString().trim())
        enableButton()
    }
    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        password.set(s.toString())
        isPassword.value =   AuthUtils.isValidPasswordFormat(s.toString().trim())
        enableButton()
    }

}