package com.dubaiculture.data.repository.eservices

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRepository
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.data.repository.eservices.mapper.transformFieldValueRequest
import com.dubaiculture.data.repository.eservices.mapper.transformFieldValuesResponse
import com.dubaiculture.data.repository.eservices.mapper.transformNocRequest
import com.dubaiculture.data.repository.eservices.remote.EServicesRDS
import com.dubaiculture.data.repository.eservices.remote.request.CreateNocRequest
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequest
import com.dubaiculture.data.repository.eservices.remote.request.GetTokenRequestParam
import com.dubaiculture.data.repository.eservices.remote.response.FormInnerResponse
import com.dubaiculture.data.repository.eservices.remote.response.GetTokenResponseDTO
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
                        transformFieldValuesResponse(index, getFieldValueResponseDTOItem)
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
        url: String
    ): Result<FormInnerResponse> {
        return when (val resultRds =
            eServiceRDS.submitForm(token, params, url)) {
            is Result.Success -> {
                if (resultRds.value.success) {
                    Result.Success(resultRds.value.data)
                } else {
                    Result.Failure(false, null, null, resultRds.value.error[0].message)
                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds
        }
    }

}