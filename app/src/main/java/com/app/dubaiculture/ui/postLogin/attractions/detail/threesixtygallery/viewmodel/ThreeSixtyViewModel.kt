package com.app.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.AttractionRepository
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ThreeSixtyViewModel @ViewModelInject constructor(
    application: Application,
    private val attractionRepository: AttractionRepository,
) : BaseViewModel(application) {
    private val _attractionDetail: MutableLiveData<Result<Attractions>> = MutableLiveData()
    val attractionDetail: LiveData<Result<Attractions>> = _attractionDetail

    fun getAttractionDetailsToScreen(
        attractionId: String,

        locale: String,
    ) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = attractionRepository.getAttractionDetailForThreeSixty(
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