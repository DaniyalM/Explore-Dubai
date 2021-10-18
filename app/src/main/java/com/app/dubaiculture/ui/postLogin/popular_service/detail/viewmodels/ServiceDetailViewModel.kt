package com.app.dubaiculture.ui.postLogin.popular_service.detail.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.popular_service.ServiceRepository
import com.app.dubaiculture.data.repository.popular_service.local.models.EServicesDetail
import com.app.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.SERVICE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceDetailViewModel @Inject constructor(
    application: Application,
    private val serviceRepository: ServiceRepository,
    val savedStateHandle: SavedStateHandle
) : BaseViewModel(application, serviceRepository) {
    private val _eServicesDetail: MutableLiveData<Result<EServicesDetail>> = MutableLiveData()
    val eServicesDetail: LiveData<Result<EServicesDetail>> = _eServicesDetail
    private val context = getApplication<ApplicationEntry>()


    init {
        savedStateHandle.get<String>(SERVICE_ID)?.let {
            getEServicesToScreen(context.auth.locale.toString(), it)
//            getEServicesToScreen(context.auth.locale.toString(), "E28578B5-3501-4B32-868C-72C73DE7A45D")
        }
    }

    fun getEServicesToScreen(locale: String, id: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                serviceRepository.getEServiceDetail(EServiceRequest(culture = locale, id = id))) {
                is Result.Success -> {
                    _eServicesDetail.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    showLoader(false)
                    _eServicesDetail.value = result
                }

            }
        }
    }


}