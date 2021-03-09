package com.app.dubaiculture.ui.postLogin.events.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.AttractionRepository
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequest
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionDetailRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class EventViewModel @ViewModelInject constructor(
    application: Application,
    private val attractionRepository: AttractionRepository,
) : BaseViewModel(application) {

    private val _attractionCategoryList: MutableLiveData<Result<ArrayList<AttractionCategory>>> =
        MutableLiveData()
    val attractionCategoryList: LiveData<Result<ArrayList<AttractionCategory>>> =
        _attractionCategoryList

    private val _attractionDetail: MutableLiveData<Result<Attractions>> = MutableLiveData()
    val attractionDetail: LiveData<Result<Attractions>> = _attractionDetail


    fun getAttractionCategoryToScreen(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                attractionRepository.getAttractionCategories(AttractionCategoryRequest(culture = locale))) {
                is Result.Success -> {
                    _attractionCategoryList.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    showLoader(false)

                    _attractionCategoryList.value = result
                }

            }
        }
    }

    fun getAttractionDetailsToScreen(attractionId: String, locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = attractionRepository.getAttractionDetail(AttractionDetailRequest(
                attractionId = attractionId,
                culture = locale))) {

                is Result.Success -> {
                    _attractionDetail.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    _attractionDetail.value = result
                    showLoader(false)
                }
            }
        }
    }
}