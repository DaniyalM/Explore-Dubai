package com.dubaiculture.ui.postLogin.popular_service.myservices.myServicesViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.eservices.EServicesRepository
import com.dubaiculture.data.repository.eservices.local.EServiceStatus
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyServicesViewModel @Inject constructor(
    application: Application,
    private val eServicesRepository: EServicesRepository
) : BaseViewModel(application) {

    private val _serviceStatusList: MutableLiveData<List<EServiceStatus>> = MutableLiveData()
    val serviceStatusList: LiveData<List<EServiceStatus>> = _serviceStatusList

    init {
        fetchServiceStatusList()
    }

    private fun fetchServiceStatusList() {
        viewModelScope.launch {
            when (val result = eServicesRepository.getServiceStatusList("en")) {
                is Result.Success -> {
                    _serviceStatusList.value = result.value
                }
                is Result.Error -> {
                    showToast(result.exception.toString())
                }
                is Result.Failure -> {
                    showToast(result.errorMessage ?: Constants.Error.SOMETHING_WENT_WRONG)
                }
            }
        }
    }
}