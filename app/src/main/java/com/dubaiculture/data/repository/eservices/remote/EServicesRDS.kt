package com.dubaiculture.data.repository.eservices.remote

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.eservices.remote.request.CreateNocRequestDTO
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequestDTO
import com.dubaiculture.data.repository.eservices.remote.request.GetTokenRequestParam
import com.dubaiculture.data.repository.eservices.remote.request.SubmitServiceTokenRequest
import com.dubaiculture.data.repository.eservices.remote.response.FormResponse
import com.dubaiculture.data.repository.eservices.remote.response.GetEServiceStatusResponse
import com.dubaiculture.data.repository.eservices.remote.response.GetFieldValueResponse
import com.dubaiculture.data.repository.eservices.remote.response.GetTokenResponse
import com.dubaiculture.data.repository.eservices.service.EService
import com.dubaiculture.data.repository.eservices.service.EServiceStatusService
import com.dubaiculture.di.EServices
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class EServicesRDS @Inject constructor(
    private val eService: EService,
    private val eServiceStatus: EServiceStatusService
) : BaseRDS() {
    suspend fun getEServiceToken(getTokenRequestParam: GetTokenRequestParam): Result<GetTokenResponse> =
        safeApiCall {
            eService.getEServiceToken(
                username = getTokenRequestParam.UserName.toRequestBody("text/plain".toMediaType()),
                password = getTokenRequestParam.Password.toRequestBody("text/plain".toMediaType())
            )
        }

    suspend fun getFieldValue(
        token: String,
        getFieldValueRequestDTO: GetFieldValueRequestDTO
    ): Result<GetFieldValueResponse> =
        safeApiCall {
            eService.getFieldValue(
                token,
                getFieldValueRequestDTO.FormName.toRequestBody("text/plain".toMediaType())
            )
        }

    suspend fun submitForm(
        token: String,
        params: HashMap<String, RequestBody>,
        url: String,
        locale: String
    ): Result<FormResponse> =
        safeApiCall {
            eService.submitForm(
                token = token,
                params = params,
                url = url,
                language = locale
            )
        }

    suspend fun submitServiceToken(
        token: String,
        serviceId: String
    ): Result<BaseResponse> =
        safeApiCall {
            eServiceStatus.submitServiceToken(
                SubmitServiceTokenRequest(token, serviceId)
            )
        }

    suspend fun getServiceStatusList(
        culture: String
    ): Result<GetEServiceStatusResponse> =
        safeApiCall {
            eServiceStatus.getServiceStatusList(
                culture
            )
        }

}