package com.app.dubaiculture.ui.preLogin.password.changepassword.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.AuthUtils

class ChangedPasswordViewModel @ViewModelInject constructor(application: Application):BaseViewModel(application){

    // editext get() and set()
    var password: ObservableField<String> = ObservableField("")
    var passwordConifrm: ObservableField<String> = ObservableField("")
    var passwordNew: ObservableField<String> = ObservableField("")

    // booleans for checking regex validation
    val isPassword = MutableLiveData<Boolean?>(true)
    val isNewPassword = MutableLiveData<Boolean?>(true)
    val isPasswordConfirm = MutableLiveData<Boolean?>(true)


    private var passwordError_ = MutableLiveData<Int>()
    var passwordError: LiveData<Int> = passwordError_

    private var passwordNewError_ = MutableLiveData<Int>()
    var passwordNewError: LiveData<Int> = passwordNewError_

    private var passwordConfirmError_ = MutableLiveData<Int>()
    var passwordConfirmError: LiveData<Int> = passwordConfirmError_

    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        password.set(s.toString())
        isPassword.value = AuthUtils.isValidPasswordFormat(s.toString().trim())
        passwordError_.value = AuthUtils.passwordErrors(s.toString().trim())
    }

    fun onNewPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        passwordNew.set(s.toString())
        isNewPassword.value = AuthUtils.isValidPasswordFormat(s.toString().trim())
        passwordNewError_.value = AuthUtils.passwordErrors(s.toString().trim())
    }

    fun onConfirmPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        passwordConifrm.set(s.toString())
        isPasswordConfirm.value = AuthUtils.isMatchPasswordBool(passwordNew.get().toString(),
            passwordConifrm.get().toString().trim())
        passwordConfirmError_.value =
            AuthUtils.isMatchPasswordError(passwordNew.get().toString().trim(),
                passwordConifrm.get().toString().trim())

    }


}