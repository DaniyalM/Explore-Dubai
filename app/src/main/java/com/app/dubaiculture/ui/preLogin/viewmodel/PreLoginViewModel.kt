package com.app.dubaiculture.ui.preLogin.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.repository.user.UserRepository
import com.app.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreLoginViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository,

    ) : BaseViewModel(application) {

    init {
        getUserIfExists()
    }


    fun getUserIfExists() {

        viewModelScope.launch {
            userRepository.getLastUser()?.let {
                setUser(it)
            }
        }
    }
}