package com.dubaiculture.ui.postLogin.profile.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.profile.ProfileRepository
import com.dubaiculture.data.repository.profile.local.Favourite
import com.dubaiculture.data.repository.user.UserRepository
import com.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSharedViewModel @Inject constructor(
    application: Application,
    private val profileRepository: ProfileRepository,
    private val userRepository: UserRepository,
) :
    BaseViewModel(application, profileRepository) {

    private val _favourite = MutableSharedFlow<Favourite>()
    val favourite = _favourite.asSharedFlow()

    init {
        getFavourites()
    }

    fun getFavourites() {
        viewModelScope.launch {
            showLoader(true)
            val result = profileRepository.getFavourites()
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _favourite.emit(result.value)
                }
                is Result.Error -> {
                    showLoader(false)

                }
                is Result.Failure -> {
                    showLoader(false)
                }
            }
        }
    }


}