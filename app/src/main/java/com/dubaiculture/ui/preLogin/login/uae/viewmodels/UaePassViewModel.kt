package com.dubaiculture.ui.preLogin.login.uae.viewmodels

import android.app.Application
import android.widget.CompoundButton
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dubaiculture.data.repository.login.LoginRepository
import com.dubaiculture.data.repository.login.remote.request.UAELoginRequest
import com.dubaiculture.data.repository.user.UserRepository
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.AuthUtils
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UaePassViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    application: Application,
) : BaseViewModel(application) {

    val btnSubmitObserver: ObservableBoolean = ObservableBoolean(false)
    var email: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")
    var agreeToTerms: ObservableBoolean = ObservableBoolean(false)

    private val _updateLinkingRequest: MutableLiveData<Event<UAELoginRequest>> = MutableLiveData()
    val updateLinkingRequest: LiveData<Event<UAELoginRequest>> = _updateLinkingRequest


    fun onCheckChange(button: CompoundButton?, check: Boolean) {
        btnSubmitObserver.set(
            AuthUtils.isEmailValid(email.toString().trim()) && password.get().toString().trim()
                .isNotEmpty() && check
        )
    }

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btnSubmitObserver.set(
            AuthUtils.isEmailValid(s.toString().trim()) && password.get().toString().trim()
                .isNotEmpty() && agreeToTerms.get()
        )
    }

    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btnSubmitObserver.set(
            AuthUtils.isEmailValid(email.get().toString().trim()) && s.toString().trim()
                .isNotEmpty() && agreeToTerms.get()
        )
    }

    fun updateLinkingRequest() {
        _updateLinkingRequest.value = Event(
            UAELoginRequest(
                email = email.get()?.trim().toString(),
                password = password.get()?.trim().toString(),
            )
        )

    }


}