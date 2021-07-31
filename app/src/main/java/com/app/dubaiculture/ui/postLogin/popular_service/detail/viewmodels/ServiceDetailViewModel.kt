package com.app.dubaiculture.ui.postLogin.popular_service.detail.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.popular_service.ServiceRepository
import com.app.dubaiculture.data.repository.popular_service.local.models.EServicesDetail
import com.app.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ServiceDetailViewModel @ViewModelInject constructor(
    application: Application,
    private val serviceRepository: ServiceRepository,
) : BaseViewModel(application, serviceRepository) {
    private val _eServicesDetail: MutableLiveData<Result<EServicesDetail>> = MutableLiveData()
    val eServicesDetail: LiveData<Result<EServicesDetail>> = _eServicesDetail


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