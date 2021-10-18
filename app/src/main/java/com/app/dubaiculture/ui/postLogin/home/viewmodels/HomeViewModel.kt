package com.app.dubaiculture.ui.postLogin.home.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.user.UserRepository
import com.app.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    val userRepository: UserRepository
) : BaseViewModel(application) {



    init {
        getUserIfExists()
    }


    fun getUserIfExists() {

        viewModelScope.launch {
            userRepository.getLastUser()?.let {
                setUser(it, true)
            }
        }
    }

    val tabDetails = mutableListOf(
        Pair(R.string.explore, R.drawable.feeds),
        Pair(R.string.events, R.drawable.marketplace),
        Pair(R.string.attractions, R.drawable.forums),
        Pair(R.string.more, R.drawable.notification)
    )

    init {

    }

}