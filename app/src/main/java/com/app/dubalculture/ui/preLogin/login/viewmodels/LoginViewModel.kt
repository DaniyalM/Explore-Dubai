package com.app.dubalculture.ui.preLogin.login.viewmodels

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.app.dubalculture.ui.base.BaseViewModel
import com.app.dubalculture.utils.AuthUtils.isEmailValid

class LoginViewModel @ViewModelInject constructor(
    application: Application
) : BaseViewModel(application) {
    var email: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")
    var btnSelected: ObservableBoolean = ObservableBoolean(false)

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


    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btnSelected.set(
            isEmailValid(s.toString().trim()) &&
                    password.get().toString().trim().length >= 6
        )
        _emailStatus.value = !isEmailValid(s.toString().trim())




    }

    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btnSelected.set(
            isEmailValid(email.get().toString().trim()) &&
                    s.toString().trim().length >= 6
        )
        _passwordStatus.value = s.toString().trim().length < 6
    }


    fun login() {
        loginStatus.value = true
//        repository?.doLogin(email?.get().toString(), password.get().toString())
    }


}