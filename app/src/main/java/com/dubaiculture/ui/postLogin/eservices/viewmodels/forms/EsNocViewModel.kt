package com.dubaiculture.ui.postLogin.eservices.viewmodels.forms

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.eservices.EServicesRepository
import com.dubaiculture.data.repository.eservices.remote.request.CreateNocRequest
import com.dubaiculture.ui.postLogin.eservices.ValueType
import com.dubaiculture.ui.postLogin.eservices.viewmodels.EServiceViewModel
import com.dubaiculture.ui.postLogin.events.detail.helper.MultipartFormHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import timber.log.Timber
import javax.inject.Inject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


@HiltViewModel
class EsNocViewModel @Inject constructor(
    application: Application,
    eServicesRepository: EServicesRepository,
    savedStateHandle: SavedStateHandle
) : EServiceViewModel(application, eServicesRepository, savedStateHandle) {

    override fun submitForm() {
        val request = HashMap<String, RequestBody>()

        val entries = getFieldMap()
        entries.forEach {
            if (it.value.first == ValueType.FILE.valueType) {
                val file = File(it.value.second)
                val fileBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                request["file"] = fileBody

//                MultipartFormHelper.getMultiPartData(it.value.second)?.let {
//                    request["File"] = it
//                }
            } else {
                request[it.key] = it.value.second.toRequestBody("text/plain".toMediaType())
            }
        }

//        val request = CreateNocRequest(
//            entries["FullName"]?.second ?: "",
//            entries["UserType"]?.second ?: "",
//            entries["Subject"]?.second ?: "",
//            entries["FilmingDate"]?.second ?: "",
//            entries["LocationAddress"]?.second ?: "",
//            entries["FromTime"]?.second ?: "",
//            entries["ToTime"]?.second ?: "",
//            entries["ContactPhoneNumber"]?.second ?: "",
//            entries["CompanyDepartment"]?.second ?: "",
//            entries["Signature"]?.second ?: "",
//            entries["UserEmailID"]?.second ?: "",
//            entries["Status"]?.second ?: "",
//            entries["StatusComments"]?.second ?: ""
//        )
        viewModelScope.launch {
            val token = getToken()
            if (token == null) {
                showLoader(false)
                return@launch
            }
            val result = eServicesRepository.createNoc(token, request)
            if (result is Result.Success) {
                showToast(result.message)
            } else {
                showToast("error")
            }
        }
    }
}