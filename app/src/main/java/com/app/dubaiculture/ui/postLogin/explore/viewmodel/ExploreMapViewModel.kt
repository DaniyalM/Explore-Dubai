package com.app.dubaiculture.ui.postLogin.explore.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.explore.ExploreRepository
import com.app.dubaiculture.data.repository.explore.local.models.AttractionsEvents
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ExploreMapViewModel @ViewModelInject constructor(application: Application,val exploreRepository: ExploreRepository):BaseViewModel(application) {

    private val _exploreAttractionsEvents: MutableLiveData<Result<AttractionsEvents>> = MutableLiveData()
    val exploreAttractionsEvents: LiveData<Result<AttractionsEvents>> = _exploreAttractionsEvents


    fun getExploreMap(locale : String){
        showLoader(true)
        viewModelScope.launch {
            when(val result = exploreRepository.getExploreMap(ExploreRequest(culture = locale))){
                is Result.Success ->{
                    showLoader(false)
                    result.value.attractionCategory
                    result.value.events

                    _exploreAttractionsEvents.value = result
                }
                is Result.Failure ->{
                    showLoader(false)


                }
                is Result.Error ->{
                    showLoader(false)
                }
            }
        }
        showLoader(false)
    }




}