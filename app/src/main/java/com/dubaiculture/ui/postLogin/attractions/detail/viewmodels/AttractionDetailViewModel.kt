package com.dubaiculture.ui.postLogin.attractions.detail.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.attraction.AttractionRepository
import com.dubaiculture.data.repository.attraction.local.models.Attractions
import com.dubaiculture.data.repository.attraction.remote.request.AttractionRequest
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.Constants.NavBundles.ATTRACTION_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttractionDetailViewModel @Inject constructor(
    application: Application,
    private val attractionRepository: AttractionRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(application, attractionRepository) {
    private val _attractionDetail: MutableLiveData<Result<Attractions>> = MutableLiveData()
    val attractionDetail: LiveData<Result<Attractions>> = _attractionDetail

    private val _attractionEvents: MutableLiveData<List<Events>> = MutableLiveData()
    val attractionEvents: LiveData<List<Events>> = _attractionEvents



    private val context = getApplication<ApplicationEntry>()

    init {
        savedStateHandle.get<Attractions>(Constants.NavBundles.ATTRACTION_OBJECT)?.let {
            getAttractionDetailsToScreen(it.id, context.auth.locale.toString())
        }
        savedStateHandle.get<String>(ATTRACTION_ID)?.let {
            getAttractionDetailsToScreen(it, context.auth.locale.toString())
        }
    }

    fun refreshDetail() {
        _attractionDetail.value = null
        savedStateHandle.get<Attractions>(Constants.NavBundles.ATTRACTION_OBJECT)?.let {
            getAttractionDetailsToScreen(it.id, context.auth.locale.toString())
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
                    _attractionEvents.value = result.value.events
//                    result.value.ibecons?.let {
//                        _beaconList.value=Event(it.ibeconItems)
//                    }

                    showLoader(false)
                }
                is Result.Failure -> {
//                    _attractionDetail.value = result
                    showLoader(false)
                }
            }
        }
    }
}