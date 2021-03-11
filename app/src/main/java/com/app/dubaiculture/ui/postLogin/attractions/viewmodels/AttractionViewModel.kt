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
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AttractionViewModel @ViewModelInject constructor(
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
                attractionRepository.getAttractionCategories(AttractionRequest(culture = locale))) {
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

    fun getAttractionDetailsToScreen(attractionId: String, pageNum:Int , pageSize:Int ,locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = attractionRepository.getAttractionDetail(AttractionRequest(
                attractionId = attractionId,
                pageNumber = pageNum,
                pageSize = pageSize,
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


//    fun getInterests(): List<AttractionCategory> {
//        val list = mutableListOf<AttractionCategory>()
//        list.add(AttractionCategory("1", "Museum", "", R.drawable.museum, R.drawable.museum))
//        list.add(AttractionCategory("2",
//            "Heritage Sites",
//            "",
//            R.drawable.heritage,
//            R.drawable.heritage))
//        list.add(AttractionCategory("3", "Festivals", "", R.drawable.festival, R.drawable.festival))
//        list.add(AttractionCategory("4",
//            "Attractions",
//            "",
//            R.drawable.museums_icon_home,
//            R.drawable.museums_icon_home))
//        return list
//    }
}