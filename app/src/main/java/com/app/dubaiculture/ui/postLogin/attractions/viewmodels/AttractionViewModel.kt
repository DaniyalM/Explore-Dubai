package com.app.dubaiculture.ui.postLogin.attractions.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.AttractionRepository
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequest
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttractionViewModel @Inject constructor(
    application: Application,
    private val attractionRepository: AttractionRepository,
) : BaseViewModel(application, attractionRepository) {
    private val context = getApplication<ApplicationEntry>()

    private val _attractionCategoryList: MutableLiveData<Result<List<AttractionCategory>>> =
        MutableLiveData()
    val attractionCategoryList: LiveData<Result<List<AttractionCategory>>> = _attractionCategoryList
    private val _attractionList: MutableLiveData<Result<List<Attractions>>> = MutableLiveData()
    val attractionList: LiveData<Result<List<Attractions>>> = _attractionList
    private val _visitedAttractionList: MutableLiveData<Event<List<Attractions>>> =
        MutableLiveData()
    val visitedAttractionList: LiveData<Event<List<Attractions>>> = _visitedAttractionList

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
        categoryId: String?,
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
                    culture = locale
                )
            )) {


                is Result.Success -> {
//                    showLoader(false)
                    _attractionList.value = result
                }
                is Result.Failure -> {
//                    showLoader(false)
                    _attractionList.value = result
                }


            }
        }
    }

    fun getVisitedAttractions(
        locale: String,
    ) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = attractionRepository.getVisitedPlaces(
//                AttractionRequest(culture = locale)
            )) {
                is Result.Success -> {
                    showLoader(false)
                    _visitedAttractionList.value = result.value
                }
                is Result.Failure -> result
                is Result.Error -> result
            }
        }
    }

    fun getAttractionDetailsToScreen(
        attractionId: String,

        locale: String,
    ) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = attractionRepository.getAttractionDetail(
                AttractionRequest(
                    attractionId = attractionId,
                    culture = locale
                )
            )) {

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