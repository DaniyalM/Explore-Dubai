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
import com.dubaiculture.utils.AuthUtils.isEmailValid
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UaePassViewModel @Inject constructor(
    application: Application,
) : BaseViewModel(application) {

    val btnSubmitObserver: ObservableBoolean = ObservableBoolean(false)
    var email: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")

    private val _updateLinkingRequest: MutableLiveData<Event<UAELoginRequest>> = MutableLiveData()
    val updateLinkingRequest: LiveData<Event<UAELoginRequest>> = _updateLinkingRequest

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btnSubmitObserver.set(
            isEmailValid(s.toString().trim()) && password.get().toString().trim().isNotEmpty()
        )
    }

    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btnSubmitObserver.set(
            isEmailValid(email.get().toString().trim()) && s.toString().trim().isNotEmpty()
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

    fun createNew(){
        _updateLinkingRequest.value= Event(
            UAELoginRequest(
                email = null,
                password = null,
                isAccountCreate = true
            )
        )

    }


}