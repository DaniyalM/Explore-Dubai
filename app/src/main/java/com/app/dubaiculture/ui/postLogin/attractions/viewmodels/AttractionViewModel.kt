package com.app.dubaiculture.ui.postLogin.attractions.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.AttractionRepository
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequest
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AttractionViewModel @ViewModelInject constructor(
    application: Application,
    private val attractionRepository: AttractionRepository,
) : BaseViewModel(application, attractionRepository) {

    private val _attractionCategoryList: MutableLiveData<Result<List<AttractionCategory>>> =
        MutableLiveData()
    val attractionCategoryList: LiveData<Result<List<AttractionCategory>>> = _attractionCategoryList
    private val _attractionList: MutableLiveData<Result<List<Attractions>>> = MutableLiveData()
    val attractionList: LiveData<Result<List<Attractions>>> = _attractionList

    private val _attractionDetail: MutableLiveData<Result<Attractions>> = MutableLiveData()
    val attractionDetail: LiveData<Result<Attractions>> = _attractionDetail



    fun getAttractionCategoryToScreen(locale: String) {
//        showLoader(true)
        viewModelScope.launch {
            when (val result =
                attractionRepository.getAttractionCategories(AttractionRequest(culture = locale))) {
                is Result.Success -> {
//                    showLoader(false)
                    _attractionCategoryList.value = result

                }
                is Result.Failure -> {
//                    showLoader(false)
                    _attractionCategoryList.value = result
                }
            }
        }
    }

    fun getAttractionThroughCategory(
        categoryId: String,
        pageNum: Int,
        pageSize: Int,
        locale: String,
    ) {
//        showLoader(true)
        viewModelScope.launch {
            when (val result = attractionRepository.getAttractionsByCategory(
                AttractionRequest(
                    attractionCategoryId = categoryId,
                    pageNumber = pageNum,
                    pageSize = pageSize,
                    culture = locale))) {
                is Result.Success -> {
                    showLoader(false)
                    _attractionList.value = result
                }
                is Result.Failure -> {
                    showLoader(false)
                    _attractionList.value = result
                }


            }
        }
    }

    fun getAttractionDetailsToScreen(
        attractionId: String,

        locale: String,
    ) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = attractionRepository.getAttractionDetail(AttractionRequest(
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