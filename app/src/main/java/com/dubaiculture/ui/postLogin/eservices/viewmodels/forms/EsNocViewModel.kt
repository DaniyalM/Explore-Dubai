package com.dubaiculture.ui.postLogin.eservices.viewmodels.forms

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.eservices.EServicesRepository
import com.dubaiculture.data.repository.eservices.remote.request.CreateNocRequest
import com.dubaiculture.ui.postLogin.eservices.viewmodels.EServiceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EsNocViewModel @Inject constructor(
    application: Application,
    eServicesRepository: EServicesRepository,
    savedStateHandle: SavedStateHandle
) : EServiceViewModel(application, eServicesRepository, savedStateHandle) {

    override fun submitForm() {
        val entries = getFieldMap()
//            .forEach {
//            Timber.e(it.key + " - " + it.value.second)
//        }
        val request = CreateNocRequest(
            entries["FullName"]?.second ?: "",
            entries["UserType"]?.second ?: "",
            entries["Subject"]?.second ?: "",
            entries["FilmingDate"]?.second ?: "",
            entries["LocationAddress"]?.second ?: "",
            entries["FromTime"]?.second ?: "",
            entries["ToTime"]?.second ?: "",
            entries["ContactPhoneNumber"]?.second ?: "",
            entries["CompanyDepartment"]?.second ?: "",
            entries["Signature"]?.second ?: "",
            entries["UserEmailID"]?.second ?: "",
            entries["Status"]?.second ?: "",
            entries["StatusComments"]?.second ?: "",
        )
        viewModelScope.launch {
            val result = eServicesRepository.createNoc(request)
            if (result is Result.Success) {
                showToast(result.message)
            } else {
                showToast("error")
            }
        }
    }
}