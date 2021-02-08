package com.app.dubaiculture.ui.postLogin.explore.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.explore.ExploreRepository
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Resource
import kotlinx.coroutines.launch

class ExploreViewModel @ViewModelInject constructor(
    application: Application,
    private val exploreRepository: ExploreRepository
) : BaseViewModel(application) {

    private val _exploreList: MutableLiveData<Result<List<Explore>>> = MutableLiveData()
    val exploreList: LiveData<Result<List<Explore>>> = _exploreList


     fun getExploreToScreen(){
        viewModelScope.launch {
            when(val result=exploreRepository.getExplore()){
                is Result.Success->{
                    _exploreList.value=result
                }
                is Result.Failure->{
                    _exploreList.value=result
                }

            }
        }
    }


}