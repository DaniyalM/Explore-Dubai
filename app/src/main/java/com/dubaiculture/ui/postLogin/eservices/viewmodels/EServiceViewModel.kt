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
import com.dubaiculture.ui.postLogin.eservices.enums.FieldType
import com.dubaiculture.ui.postLogin.eservices.enums.FormType
import com.dubaiculture.ui.postLogin.eservices.enums.ValidationType
import com.dubaiculture.ui.postLogin.eservices.enums.ValueType
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.Constants.NavBundles.FORM_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
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

    private val map: HashMap<GetFieldValueItem, String> by lazy {
        HashMap()
    }

    private val form = FormType.NOC_FORM

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

    fun submitForm(isArabic: Boolean) {
        showLoader(true)
        val request = HashMap<String, RequestBody>()

        val fields = _fieldValues.value?.filter {
            (FieldType.fromName(it.fieldType) != null || ValueType.fromName(it.valueType) != null)
        } ?: listOf()

        fields.forEach {
            val value = map[it] ?: ""
            validate(isArabic, it, value)?.let { errorMessage ->
                showToast(errorMessage)
                showLoader(false)
                return
            }
            if (it.valueType == ValueType.FILE.valueType) {
                val file = File(value)
                val fileBody = file.asRequestBody(Constants.MimeType.ALL.toMediaType())
                val key = "${it.fieldName}\"; filename=\"${value}\""
                request[key] = fileBody
            } else {
                request[it.fieldName] =
                    value.toRequestBody(Constants.MimeType.TEXT.toMediaType())
            }
        }

        viewModelScope.launch {
            val token = getToken()
            if (token == null) {
                showLoader(false)
                return@launch
            }
            val result = eServicesRepository.submitForm(
                token,
                request,
                form.url,
                if (isArabic) Constants.Locale.ARABIC else Constants.Locale.ENGLISH
            )
            if (result is Result.Success) {
                showToast(result.message)
            } else if (result is Result.Failure) {
                showToast(result.errorMessage ?: Constants.Error.SOMETHING_WENT_WRONG)
            }
            showLoader(false)
        }
    }

    private fun validate(
        isArabic: Boolean,
        field: GetFieldValueItem,
        value: String
    ): String? {
        field.validations.forEach {
            if (it.validationType.equals(ValidationType.PATTERN.type, true)) {
                if (!it.pattern.toRegex().matches(value)) {
                    return if (isArabic) it.arabicMsg else it.englishMsg
                }
            } else if (it.validationType.equals(ValidationType.REQUIRED.type, true)) {
                if (value.isEmpty()) {
                    return if (isArabic) it.arabicMsg else it.englishMsg
                }
            }
        }
        return null
    }
}