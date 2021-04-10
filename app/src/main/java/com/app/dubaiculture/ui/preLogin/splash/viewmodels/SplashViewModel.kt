package com.app.dubaiculture.ui.preLogin.splash.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.repository.user.UserRepository
import com.app.dubaiculture.data.repository.user.local.User
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    application: Application,
) : BaseViewModel(application) {
    fun getUserIfExists(): User? {
        var user: User? = null
        viewModelScope.launch {
            userRepository.getLastUser()?.let {
                user = it
            }
        }
        return user
    }
}