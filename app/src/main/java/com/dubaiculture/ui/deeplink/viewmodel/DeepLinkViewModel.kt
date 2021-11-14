package com.dubaiculture.ui.deeplink.viewmodel

import android.app.Application
import com.dubaiculture.data.repository.user.UserRepository
import com.dubaiculture.data.repository.user.local.User
import com.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeepLinkViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : BaseViewModel(application) {

    suspend fun getUser(): User? = userRepository.getLastUser()

}