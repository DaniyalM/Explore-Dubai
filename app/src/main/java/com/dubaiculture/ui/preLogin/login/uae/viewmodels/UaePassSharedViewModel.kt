package com.dubaiculture.ui.preLogin.login.uae.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dubaiculture.data.repository.login.LoginRepository
import com.dubaiculture.data.repository.login.remote.request.UAELoginRequest
import com.dubaiculture.data.repository.user.UserRepository
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UaePassSharedViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    application: Application,
) : BaseViewModel(application) {
    private val _isLinkingRequest: MutableLiveData<Event<UAELoginRequest>> = MutableLiveData()
    val isLinkingRequest: LiveData<Event<UAELoginRequest>> = _isLinkingRequest

    private val _sheetOpen: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val sheetOpen: LiveData<Event<Boolean>> = _sheetOpen


     fun registerWithUaePass(uaeLoginRequest: UAELoginRequest){
        _isLinkingRequest.value= Event(uaeLoginRequest)
    }


}