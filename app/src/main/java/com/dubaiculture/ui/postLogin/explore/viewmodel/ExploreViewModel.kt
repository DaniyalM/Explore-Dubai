package com.dubaiculture.ui.postLogin.explore.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.explore.ExploreRepository
import com.dubaiculture.data.repository.explore.local.models.Explore
import com.dubaiculture.data.repository.explore.remote.request.ExploreRequest
import com.dubaiculture.data.repository.user.UserRepository
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    application: Application,
    private val exploreRepository: ExploreRepository,
    private val userRepository: UserRepository
) : BaseViewModel(application, exploreRepository) {
    private val context = getApplication<ApplicationEntry>()

    private val _exploreList: MutableLiveData<Result<List<Explore>>> = MutableLiveData()
    val exploreList: LiveData<Result<List<Explore>>> = _exploreList

    init {
        getExploreToScreen(context.auth.locale.toString())
    }

    fun getExploreToScreen(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = exploreRepository.getExplore(ExploreRequest(culture = locale))) {
                is Result.Success -> {
                    _exploreList.value = result
                    showLoader(false)
                }
                is Result.Failure -> showLoader(false)
//                {

//                    if (result.errorCode == HTTP_400) {
//                        userRepository.getLastUser()?.let {
//                            userRepository.deleteUser(it)
//                            context.auth.user = null
//                            con
//                        }
//                    }
//                }

            }
//            showLoader(false)
        }

    }


}