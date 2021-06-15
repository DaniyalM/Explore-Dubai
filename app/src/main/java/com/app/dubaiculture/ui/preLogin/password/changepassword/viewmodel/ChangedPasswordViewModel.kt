package com.app.dubaiculture.ui.preLogin.password.changepassword.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.login.LoginRepository
import com.app.dubaiculture.data.repository.login.remote.request.changedpass.ChangedPassRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.AuthUtils
import com.app.dubaiculture.utils.event.Event
import kotlinx.coroutines.launch

class ChangedPasswordViewModel @ViewModelInject constructor(application: Application,val loginRepository: LoginRepository):BaseViewModel(application){

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



    private var _changedPasswordStatus: MutableLiveData<Event<Boolean>> = MutableLiveData()
    var changedPasswordStatus: MutableLiveData<Event<Boolean>> = _changedPasswordStatus

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

    fun changedPassword(){
        if (!isCheckValidation())
            return
        viewModelScope.launch {
            showLoader(true)
          ChangedPassRequest(
                    oldPass = password.get().toString().trim(),
                    newPass = passwordNew.get().toString().trim(),
                    confirmPass = passwordConifrm.get().toString().trim()
            ).let {
              when(val result = loginRepository.changedPassword(it)){
                  is Result.Success->{
                      showLoader(false)
                      showErrorDialog( message =    result.value.changedPasswordResponseDTO.message, colorBg = R.color.purple_900)
                      _changedPasswordStatus.value = Event(true)
                  }
                  is Result.Failure ->{
                      showErrorDialog( message =    result.errorMessage.toString())
                      showLoader(false)

                  }
              }
            }
        }
    }
    private fun isCheckValidation() : Boolean{
        var isValid = true
        passwordError_.value = AuthUtils.passwordErrors(password.get().toString().trim())
        passwordNewError_.value = AuthUtils.passwordErrors(passwordNew.get().toString().trim())
        passwordConfirmError_.value = AuthUtils.isMatchPasswordError(passwordNew.get().toString().trim(), passwordConifrm.get().toString().trim())

        isPasswordConfirm.value = AuthUtils.isMatchPasswordBool(passwordNew.get().toString(),
            passwordConifrm.get().toString().trim())
        isPassword.value = AuthUtils.isValidPasswordFormat(password.get().toString().trim())
        isNewPassword.value = AuthUtils.isValidPasswordFormat(passwordNew.get().toString().trim())



        if (!AuthUtils.isValidPasswordFormat(password.get().toString().trim())) {
            isValid = false
        }
        if (!AuthUtils.isValidPasswordFormat(passwordNew.get().toString().trim())) {
            isValid = false
        }
        if (!AuthUtils.isMatchPasswordBool(passwordNew.get().toString().trim(),
                        passwordConifrm.get().toString().trim())
        ) {
            isValid = false
        }
        return isValid
    }
}