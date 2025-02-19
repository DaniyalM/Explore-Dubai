package com.dubaiculture.data.repository.eservices

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRepository
import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.eservices.local.EServiceStatus
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.data.repository.eservices.mapper.transformEServiceStatus
import com.dubaiculture.data.repository.eservices.mapper.transformFieldValueRequest
import com.dubaiculture.data.repository.eservices.mapper.transformFieldValuesResponse
import com.dubaiculture.data.repository.eservices.remote.EServicesRDS
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequest
import com.dubaiculture.data.repository.eservices.remote.request.GetTokenRequestParam
import com.dubaiculture.data.repository.eservices.remote.response.FormInnerResponse
import com.dubaiculture.data.repository.eservices.remote.response.FormResponse
import com.dubaiculture.data.repository.eservices.remote.response.GetTokenResponseDTO
import com.dubaiculture.utils.Constants
import okhttp3.RequestBody
import javax.inject.Inject

class EServicesRepository @Inject constructor(
    private val eServiceRDS: EServicesRDS
) :
    BaseRepository() {
    suspend fun getEServiceToken(getTokenRequestParam: GetTokenRequestParam): Result<GetTokenResponseDTO> =
        when (val resultRds = eServiceRDS.getEServiceToken(getTokenRequestParam)) {
            is Result.Success -> {
                if (resultRds.value.success) {
                    Result.Success(resultRds.value.getTokenResponseDTO)
                } else {
                    Result.Failure(false, null, null, resultRds.value.error[0].message)
                }
            }
            is Result.Error -> resultRds
            is Result.Failure -> resultRds
        }

    suspend fun getFieldValue(
        token: String,
        getFieldValueRequest: GetFieldValueRequest
    ): Result<List<GetFieldValueItem>> =
        when (val resultRds =
            eServiceRDS.getFieldValue(token, transformFieldValueRequest(getFieldValueRequest))) {
            is Result.Success -> {
                if (resultRds.value.success) {
                    Result.Success(resultRds.value.getFieldValueResponseDTO.mapIndexed { index, getFieldValueResponseDTOItem ->
                        transformFieldValuesResponse(getFieldValueResponseDTOItem)
                    }
                    )
                } else {
                    Result.Failure(false, null, null, resultRds.value.error[0].message)
                }
            }
            is Result.Error -> resultRds
            is Result.Failure -> resultRds
        }

    suspend fun submitForm(
        token: String,
        params: HashMap<String, RequestBody>,
        url: String,
        locale: String
    ): Result<FormResponse> {
        return when (val resultRds =
            eServiceRDS.submitForm(token, params, url, locale)) {
            is Result.Success -> {
                if (resultRds.value.success) {
                    Result.Success(resultRds.value)
                } else {
                    Result.Failure(
                        false,
                        null,
                        null,
                        resultRds.value.error.firstOrNull()?.message
                            ?: Constants.Error.SOMETHING_WENT_WRONG
                    )
                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds
        }
    }

    suspend fun submitServiceToken(
        token: String,
        serviceId: String
    ): Result<BaseResponse> {
        return when (val resultRds =
            eServiceRDS.submitServiceToken(token, serviceId)) {
            is Result.Success -> {
                if (resultRds.value.succeeded) {
                    Result.Success(resultRds.value)
                } else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds
        }
    }

    suspend fun getServiceStatusList(
        culture: String,
    ): Result<List<EServiceStatus>> {
        return when (val resultRds =
            eServiceRDS.getServiceStatusList(culture)) {
            is Result.Success -> {
                if (resultRds.value.succeeded) {
                    Result.Success(
                        resultRds.value.result.services.map {
                            transformEServiceStatus(it)
                        }
                    )
                } else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds
        }
    }

}