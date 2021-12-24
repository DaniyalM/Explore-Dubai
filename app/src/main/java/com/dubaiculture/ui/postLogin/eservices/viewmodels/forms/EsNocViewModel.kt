package com.dubaiculture.ui.postLogin.eservices.viewmodels.forms

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.dubaiculture.data.repository.eservices.EServicesRepository
import com.dubaiculture.ui.postLogin.eservices.viewmodels.EServiceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EsNocViewModel @Inject constructor(
    application: Application,
    eServicesRepository: EServicesRepository,
    savedStateHandle: SavedStateHandle
) : EServiceViewModel(application, eServicesRepository, savedStateHandle) {

    override fun submitForm() {
        getFieldMap().entries.forEach {
            Timber.e(it.key.fieldName + " - " + it.value)
        }
    }
}