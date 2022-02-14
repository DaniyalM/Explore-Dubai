package com.dubaiculture.ui.postLogin.eservices.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.eservices.EServicesRepository
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequest
import com.dubaiculture.data.repository.eservices.remote.request.GetTokenRequestParam
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.ui.postLogin.eservices.enums.FormType
import com.dubaiculture.ui.postLogin.eservices.enums.ValidationType
import com.dubaiculture.ui.postLogin.eservices.enums.ValueType
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.Constants.NavBundles.FORM_NAME
import com.dubaiculture.utils.Constants.NavBundles.FORM_URL
import com.dubaiculture.utils.Constants.NavBundles.SERVICE_ID
import com.dubaiculture.utils.event.Event
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
    var emiratesId: String? = null

    private val map: HashMap<String, String> by lazy {
        HashMap()
    }

    private val _fieldValues: MutableLiveData<List<GetFieldValueItem>> = MutableLiveData()
    val fieldValues: LiveData<List<GetFieldValueItem>> = _fieldValues

    private val _showField: MutableLiveData<Event<Pair<Boolean, GetFieldValueItem>>> =
        MutableLiveData()
    val showField: LiveData<Event<Pair<Boolean, GetFieldValueItem>>> = _showField

    private val _makeOptional: MutableLiveData<Event<Pair<Boolean, GetFieldValueItem>>> =
        MutableLiveData()
    val makeOptional: LiveData<Event<Pair<Boolean, GetFieldValueItem>>> = _makeOptional

    private var mFieldValues: List<GetFieldValueItem>? = null

    init {
        emiratesId = (application as ApplicationEntry).auth.user?.idn
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
                    mFieldValues =
                        if (emiratesId.isNullOrEmpty()) result.value else setupEmiratesId(
                            emiratesId!!,
                            result.value
                        )
                    _fieldValues.value = mFieldValues
                    showHideFields(formName)
                }
                is Result.Failure -> {
                    showLoader(false)
                }
            }
        }
    }

    private fun showHideFields(formName: String) {
        if (formName.equals(FormType.BOOKING_ESERVICE.value, false)) {
            showField("Entity", false)
            showField("Company", false)
        } else if (formName.equals(FormType.SUPPLIER_REGISTRATION.value, false)) {
            showField("OtherLicenseActivities", false)
        }
    }

    private fun setupEmiratesId(
        emiratesId: String,
        list: List<GetFieldValueItem>
    ): List<GetFieldValueItem> {
        return list.map {
            if (isEmiratesId(it.fieldName)) {
                it.copy(defaultValue = emiratesId)
            } else it
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
            showToast(R.string.something_went_wrong)
            null
        }
    }

    fun addField(fieldName: String, value: String, isSelected: Boolean = false) {
        val cleanedValue = getCleanedValue(value)
        map[fieldName] = cleanedValue

        if (fieldName == "IsVisa") {
            makeFieldOptional("EmiratesID", isSelected)
            makeFieldOptional("EmiratesIDCopy", isSelected)
        } else if (fieldName == "isLiveDubai") {
            makeFieldOptional("VisaCopy", isSelected)
            makeFieldOptional("VisaEmirate", isSelected)
        } else if (fieldName == "DubaiSME") {
            makeFieldOptional("MembershipCertificate", cleanedValue != "Yes")
        } else if (fieldName == "Country") {
            showField("City", cleanedValue == "United Arab Emirates")
        } else if (fieldName == "BookingType" && cleanedValue == "Company") {
            showField("Company", true)
            showField("Entity", false)
        } else if (fieldName == "BookingType" && cleanedValue == "Government") {
            showField("Entity", true)
            showField("Company", false)
        } else if (fieldName == "BookingType" && cleanedValue == "Individual") {
            showField("Entity", false)
            showField("Company", false)
        } else if (fieldName == "LicenseActivities") {
            showField("OtherLicenseActivities", cleanedValue == "Other")
        }
    }

    private fun makeFieldOptional(
        fieldName: String,
        makeOptional: Boolean
    ) {
        mFieldValues?.firstOrNull {
            fieldName == it.fieldName
        }?.let {
            _makeOptional.value = Event(Pair(makeOptional, it))
        }
        mFieldValues = mFieldValues?.map {
            if (fieldName == it.fieldName) {
                it.copy(
                    isRequired = !makeOptional
                )
            } else it
        }
    }

    private fun showField(fieldName: String, show: Boolean) {
        mFieldValues?.firstOrNull {
            it.fieldName == fieldName
        }?.let {
            _showField.value = Event(Pair(show, it))
        }
        mFieldValues = mFieldValues?.map {
            if (it.fieldName == fieldName) {
                it.copy(isRequired = show)
            } else it
        }
    }

    fun submitForm(isArabic: Boolean) {
        showLoader(true)
        val request = HashMap<String, RequestBody>()

        val fields = mFieldValues?.filter {
//            (FieldType.fromName(it.fieldType) != null ||
            ValueType.fromName(it.valueType) != null
            //)
        } ?: listOf()

        fields.forEach {
            val value = map[it.fieldName] ?: ""
            validate(isArabic, it, value)?.let { errorMessage ->
                showToast(errorMessage)
                showLoader(false)
                return
            }
            if (it.valueType != ValueType.FILE.valueType) {
                if (!it.isRequired && value.isEmpty()) {

                } else {
                    request[it.fieldName] =
                        value.toRequestBody(Constants.MimeType.TEXT.toMediaType())

                }
            } else if (it.valueType == ValueType.FILE.valueType && value.isNotEmpty()) {
                val file = File(value)
                val fileBody = file.asRequestBody(Constants.MimeType.ALL.toMediaType())
                val key = "${it.fieldName}\"; filename=\"${value}\""
                request[key] = fileBody
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
                savedStateHandle.get<String>(FORM_URL) ?: "",
                if (isArabic) Constants.Locale.ARABIC else Constants.Locale.ENGLISH
            )
            if (result is Result.Success) {
                eServicesRepository.submitServiceToken(
                    result.value.data.SerialNo,
                    savedStateHandle.get<String>(SERVICE_ID) ?: ""
                )
                showAlert(
                    message = result.value.message + "\n" + result.value.data.SerialNo,
                    actionPositive = {
                        navigateByBack()
                    })
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
        if (!field.isRequired && value.isEmpty()) return null // donot validate non required fields
        field.validations.forEach {
            if (it.validationType.equals(ValidationType.PATTERN.type, true)) {
                if (it.pattern.isNotEmpty() && !it.pattern.toRegex().matches(value)) {
                    return if (isArabic) it.arabicMsg else it.englishMsg
                }
            } else if (it.validationType.equals(ValidationType.REQUIRED.type, true)) {
                if (field.isRequired && value.isEmpty()) {
                    return if (isArabic) it.arabicMsg else it.englishMsg
                }
            }
        }
        return null
    }

    fun showFutureDates(fieldName: String) =
        fieldName != "BirthDate" && fieldName != "TradeLicenseIssueDate"

    fun showPastDates(field: GetFieldValueItem) =
        !(field.formName.equals(
            FormType.BOOKING_ESERVICE.value,
            true
        ) && field.fieldName == "Date")
                && !(field.formName.equals(
            FormType.RENT_REQUEST.value,
            true
        ) && field.fieldName == "PriorDate")
                && !(field.formName.equals(
            FormType.SUPPLIER_REGISTRATION.value,
            true
        ) && field.fieldName == "TradeLicenseExpiryDate")

    fun isEmiratesId(fieldName: String) = fieldName == "EmiratesID"
    fun getCleanedValue(value: String) = value.replace("\u00a0", " ")
}