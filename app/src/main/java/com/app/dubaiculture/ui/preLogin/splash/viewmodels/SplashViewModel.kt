package com.app.dubaiculture.ui.preLogin.splash.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.repository.user.UserRepository
import com.app.dubaiculture.data.repository.user.local.User
import com.app.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    application: Application,
) : BaseViewModel(application) {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user

    fun getUserIfExists() {

        viewModelScope.launch {
            userRepository.getLastUser()?.let {
                _user.value = it

            }
        }
    }

    fun removeUser(user: User) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }
}