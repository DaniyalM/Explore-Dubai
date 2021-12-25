package com.dubaiculture.ui.postLogin.eservices.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.BuildConfig
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.eservices.EServicesRepository
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequest
import com.dubaiculture.data.repository.eservices.remote.request.GetTokenRequestParam
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants.NavBundles.FORM_NAME
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class EServiceViewModel constructor(
    application: Application,
    val eServicesRepository: EServicesRepository,
    val savedStateHandle: SavedStateHandle
) :
    BaseViewModel(application) {
    //Map -> Field Name, Pair(DisplayValue,FormValue)
    private val map: HashMap<String, Pair<String, String>> by lazy {
        HashMap()
    }

    fun getFieldMap() = map

    private val _fieldValues: MutableLiveData<List<GetFieldValueItem>> = MutableLiveData()
    val fieldValues: LiveData<List<GetFieldValueItem>> = _fieldValues

    init {
        savedStateHandle.get<String>(FORM_NAME)?.let {
            getFieldValues(it)
        }
    }

    private fun getFieldValues(formName: String) {
        showLoader(true)
        viewModelScope.launch {
            val token = getToken()
            if (token == null) {
                showLoader(false)
                return@launch
            }
            when (val result =
                eServicesRepository.getFieldValue(
                    token,
                    GetFieldValueRequest(formName = formName)
                )) {
                is Result.Success -> {
                    showLoader(false)
                    _fieldValues.value = result.value
                }
                is Result.Failure -> {
                    showLoader(false)
                }
            }
        }
    }

    suspend fun getToken(): String? {
        val result = eServicesRepository.getEServiceToken(
            GetTokenRequestParam(
                BuildConfig.ESERVICES_USERNAME,
                BuildConfig.ESERVICES_PASSWORD
            )
        )
        return if (result is Result.Success) {
            result.value.token
        } else {
            showToast("Token generation failed")
            null
        }
    }

    fun addField(fieldName: String, displayValue: String, value: String) {
        map[fieldName] = Pair(displayValue, value)
    }

    abstract fun submitForm()

}