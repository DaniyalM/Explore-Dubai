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
import com.dubaiculture.ui.postLogin.eservices.enums.FormType
import com.dubaiculture.ui.postLogin.eservices.enums.ValueType
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.Constants.NavBundles.FORM_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EServiceViewModel @Inject constructor(
    application: Application,
    val eServicesRepository: EServicesRepository,
    val savedStateHandle: SavedStateHandle
) :
    BaseViewModel(application) {
    var selectedView: GetFieldValueItem? = null

    //Map -> FieldName, Pair(ValueType, FormValue)
    private val map: HashMap<GetFieldValueItem, String> by lazy {
        HashMap()
    }

    fun getFieldMap() = map
    private val form = FormType.SUPPLIER_REGISTRATION
    private val _fieldValues: MutableLiveData<List<GetFieldValueItem>> = MutableLiveData()
    val fieldValues: LiveData<List<GetFieldValueItem>> = _fieldValues

    init {
        savedStateHandle.get<String>(FORM_NAME)?.let {
            getFieldValues(form.value)
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

    fun addField(field: GetFieldValueItem, value: String) {
        map[field] = value
    }

    fun submitForm() {
        showLoader(true)
        val request = HashMap<String, RequestBody>()

        val entries = getFieldMap()
        entries.forEach {
            if (it.key.valueType == ValueType.FILE.valueType) {
                val file = File(it.value)
                val fileBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                val key = "${it.key.fieldName}\"; filename=\"${it.value}\""
                request[key] = fileBody
            } else {
                request[it.key.fieldName] = it.value.toRequestBody("text/plain".toMediaType())
            }
        }

        viewModelScope.launch {
            val token = getToken()
            if (token == null) {
                showLoader(false)
                return@launch
            }
            val result = eServicesRepository.submitForm(token, request, form.url)
            if (result is Result.Success) {
                showToast(result.message)
            } else if (result is Result.Failure) {
                showToast(result.errorMessage ?: Constants.Error.SOMETHING_WENT_WRONG)
            }
            showLoader(false)
        }
    }

}