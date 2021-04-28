package com.app.dubaiculture.ui.postLogin.explore.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.explore.ExploreRepository
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ExploreViewModel @ViewModelInject constructor(
    application: Application,
    private val exploreRepository: ExploreRepository
) : BaseViewModel(application) {

    private val _exploreList: MutableLiveData<Result<List<Explore>>> = MutableLiveData()
    val exploreList: LiveData<Result<List<Explore>>> = _exploreList


    fun getExploreToScreen(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = exploreRepository.getExplore(locale)) {
                is Result.Success -> {
                    _exploreList.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    showLoader(false)
                    _exploreList.value = result
                }

            }
            showLoader(false)
        }

    }


}