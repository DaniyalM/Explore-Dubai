package com.app.dubaiculture.ui.postLogin.attractions.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.AttractionRepository
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AttractionViewModel @ViewModelInject constructor(
    application: Application,
    private val attractionRepository: AttractionRepository,
) : BaseViewModel(application) {

    private val _attractionCategoryList: MutableLiveData<Result<List<AttractionCategory>>> =
        MutableLiveData()
    val attractionCategoryList: LiveData<Result<List<AttractionCategory>>> = _attractionCategoryList


    fun getAttractionCategoryToScreen(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = attractionRepository.getAttractionCategories(
                AttractionCategoryRequest(culture = locale))) {
                is Result.Success -> {
                    _attractionCategoryList.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    _attractionCategoryList.value = result
                }

            }
        }
    }


    fun getInterests(): List<AttractionCategory> {
        val list = mutableListOf<AttractionCategory>()
        list.add(AttractionCategory("1", "Museum", "", R.drawable.museum, R.drawable.museum))
        list.add(AttractionCategory("2",
            "Heritage Sites",
            "",
            R.drawable.heritage,
            R.drawable.heritage))
        list.add(AttractionCategory("3", "Festivals", "", R.drawable.festival, R.drawable.festival))
        list.add(AttractionCategory("4",
            "Attractions",
            "",
            R.drawable.museums_icon_home,
            R.drawable.museums_icon_home))
        return list
    }
}