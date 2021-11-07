package com.dubaiculture.ui.postLogin.attractions.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.attraction.AttractionRepository
import com.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.dubaiculture.data.repository.attraction.local.models.Attractions
import com.dubaiculture.data.repository.attraction.remote.request.AttractionRequest
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants.NavBundles.NEW_LOCALE
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttractionViewModel @Inject constructor(
    application: Application,
    private val attractionRepository: AttractionRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(application, attractionRepository) {
    private val context = getApplication<ApplicationEntry>()

    private val _attractionCategoryList: MutableLiveData<Result<List<AttractionCategory>>> =
        MutableLiveData()
    val attractionCategoryList: LiveData<Result<List<AttractionCategory>>> = _attractionCategoryList

    private val _visitedAttractionList: MutableLiveData<Event<List<Attractions>>> =
        MutableLiveData()
    val visitedAttractionList: LiveData<Event<List<Attractions>>> = _visitedAttractionList


    init {
        getAttractionCategoryToScreen(context.auth.locale.toString())
    }

    fun refreshList(){
        _attractionCategoryList.value=null
        getAttractionCategoryToScreen(context.auth.locale.toString())
    }

    fun getAttractionCategoryToScreen(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                attractionRepository.getAttractionCategories(AttractionRequest(culture = locale))) {
                is Result.Success -> {
                    showLoader(false)
                    _attractionCategoryList.value = result

                }
                is Result.Failure -> {
                    showLoader(false)
                    _attractionCategoryList.value = result
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
                AttractionRequest(culture = locale)
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
}