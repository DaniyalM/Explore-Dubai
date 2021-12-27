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
import com.dubaiculture.ui.postLogin.eservices.FormType
import com.dubaiculture.ui.postLogin.eservices.ValueType
import com.dubaiculture.utils.Constants.NavBundles.FORM_NAME
import com.dubaiculture.utils.event.Event
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
    private val map: HashMap<String, Pair<String, String>> by lazy {
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
        map[field.fieldName] = Pair(field.valueType, value)
    }

    fun submitForm() {
        val request = HashMap<String, RequestBody>()

        val entries = getFieldMap()
        entries.forEach {
            if (it.value.first == ValueType.FILE.valueType) {
                val file = File(it.value.second)
                val fileBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                val key = "file\"; filename=\"${it.value.second}\""
                request[key] = fileBody
            } else {
                request[it.key] = it.value.second.toRequestBody("text/plain".toMediaType())
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
            } else {
                showToast("error")
            }
        }
    }

}